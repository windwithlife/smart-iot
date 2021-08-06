package com.simple.bz.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simple.bz.dao.*;
import com.simple.bz.dto.*;
import com.simple.bz.model.*;
import com.simple.common.mqtt.MqttAdapter;
import com.simple.common.mqtt.MqttProxy;
import com.simple.common.redis.MessageQueueProxy;
import com.simple.common.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IOTService extends MqttAdapter {

    private final MqttProxy mqttProxy;
    private final MessageQueueProxy mqProxy;
    private final HouseService houseService;
    private final WebSocketService webSocketService;
    private final DeviceService deviceService;
    private final GatewayDeviceService gatewayDeviceService;
    private final DeviceRepository deviceDao;
    private final DeviceClusterRepository clusterDao;
    private final GatewayDeviceRepository gatewayDao;
    private final DeviceStatusAttributeRepository deviceStatusAttributeRepository;
    private final ContextQuery contextQuery;

    @PostConstruct
    private void init() {

        mqttProxy.registerHandler("tele/", this);
        mqttProxy.registerHandler("stat/", this);
        mqProxy.registerMessageHandler("test",new RedisSubscriber());

    }


    @Override
    public void handleMessage(String topic, String payload) {

        String statusType = "tele";
        String gatewayDevice = "";
        String command = "";
        if ((!topic.startsWith("tele")) && (!topic.startsWith("stat"))) {
            //System.out.println("Not IOT Message !");
            return;
        } else {
            //System.out.println("Process in IOT service! start!" );
            System.out.println("Process in IOT service! Topic ===>【" + topic + "】 Payload===>" + payload);
            String[] params = StringUtils.split(topic, "/");
            if (params.length <= 2) {
                System.out.println("message is invalid!");
            }
            statusType = params[0];
            gatewayDevice = params[1];
            command = params[2];
            System.out.println("statusType==>【" + statusType + "】 gatewayDevice==>【" + gatewayDevice + "】 command ==>" + command);
        }
        String topicFormat = String.format("%s/#/%s", statusType, command);
        GatewayDeviceModel gatewayInfo = gatewayDao.findOneByLocationTopic(gatewayDevice);
        if(null == gatewayInfo){
            System.out.println("Received unregistered gateway message! gateway topic ===>[" + gatewayDevice + "]");
            return;
        }
        switch (topicFormat) {
            case "stat/#/POWER": // OFF/ON
                //doPower(gatewayMacAddress,payload);
                break;
            case "tele/#/RESULT":
                //{"ZbState":{"Status":21,"Message":"Pairing mode enabled"}}
                //{"ZbPermitJoin":"Done"}
                //{"ZbState":{"Status":20,"Message":"Pairing mode disabled"}}

                // {"ZbState":{"Status":34,"IEEEAddr":"0x000D6FFFFED209BF","ShortAddr":"0x7984","ParentNetwork":"0x0000","JoinStatus":0,"Decision":3}}
                //{"ZbState":{"Status":30,"IEEEAddr":"0x000D6FFFFED209BF","ShortAddr":"0x7984","PowerSource":false,"ReceiveWhenIdle":false,"Security":false}}

                //{"ZbReceived":{"0x7984":{"Device":"0x7984","BatteryPercentage":88,"0001/0100":0,"Endpoint":1,"LinkQuality":120}}}
                //doZbState(gatewayMacAddress,payload);
                this.processDevicePairing(gatewayInfo.getId(), payload);
                break;
            case "tele/#/STATE":
                //STATE = {"Time":"2021-06-19T00:37:17","Uptime":"0T00:00:09","UptimeSec":9,"Vcc":3.423,"Heap":27,"SleepMode":"Dynamic","Sleep":50,"LoadAvg":19,"MqttCount":1,"Wifi":{"AP":1,"SSId":"********","BSSId":"BC:2E:F6:DC:39:64","Channel":6,"RSSI":62,"Signal":-69,"LinkCount":1,"Downtime":"0T00:00:03"}}
                GatewayStatusDto dto = JSON.parseObject(payload, GatewayStatusDto.class);
                System.out.println("have parse gateway device status json object ===>" + dto.toString());
                this.processGatewayStatus(gatewayInfo.getId(), dto);
            case "tele/#/LWT":
                this.processGatewayOffline(gatewayInfo.getId(), payload);
                break;
            case "tele/#/INFO1":
                //doInfo1(gatewayMacAddress,payload);
                break;
            case "tele/#/INFO2":
                //doInfo2(gatewayMacAddress,payload);
                break;
            case "tele/#/INFO3":
                //doInfo3(gatewayMacAddress,payload);
                break;
            case "tele/#/SENSOR":
                this.processesSensorStatus(gatewayInfo.getId(),payload);
                break;
        }

    }

    @Transactional
    private void processDevicePairing(Long gatewayId, String payload) {
        JSONObject jsonObject = JSON.parseObject(payload);
        if (jsonObject.get("ZbState") instanceof JSONObject &&
                ((JSONObject) jsonObject.get("ZbState")).containsKey("Status")) {

            int statusCode = ((JSONObject) jsonObject.get("ZbState")).getInteger("Status");
            DeviceModel deviceResult = null;
            JSONObject stateJsonObject = (JSONObject) jsonObject.get("ZbState");
            switch (statusCode) {
                case 30:
                    //有设备连接上网关，插入设备表
                    //{"ZbState":{"Status":30,"IEEEAddr":"0x000D6FFFFED209BF","ShortAddr":"0x7984","PowerSource":false,"ReceiveWhenIdle":false,"Security":false}}
                    try {
                        DeviceState30 state30 = stateJsonObject.toJavaObject(DeviceState30.class);

                        if (state30 == null) {
                            break;
                        }
                        DeviceModel oldDevice = deviceDao.findOneByIeeeAndGatewayId(state30.getIEEEAddr(),gatewayId);
                        if (null != oldDevice) { //设备已存在过
                            oldDevice.setShortAddress(state30.getShortAddr());
                            deviceDao.save(oldDevice);
                            //deviceDao.deleteById(oldDevice.getId());
                            //clusterDao.deleteByDeviceId(oldDevice.getId());
                            // this.contextQuery.executeQuery("delete from tbl_device_cluster where deviceId=" + String.valueOf(oldDevice.getId()));
                        }else{
                            DeviceModel device = DeviceModel.builder().createTime(new Date()).ieee(state30.getIEEEAddr()).shortAddress(state30.getShortAddr()).powerSource(state30.getPowerSource())
                                    .receiveWhenIdle(state30.getReceiveWhenIdle()).gatewayId(gatewayId).build();
                            deviceResult = deviceDao.save(device);
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }

                    break;
                case 33:
                    //Received the simple Descriptor with active ZCL clusters for a Zigbee device
                    // {"ZbState":{"Status":33,"Device":"0x7984","Endpoint":"0x01","ProfileId":"0x0104","DeviceId":"0x0402","DeviceVersion":0,"InClusters":
                    DeviceState33 state33 = stateJsonObject.toJavaObject(DeviceState33.class);
                    System.out.println("Recieved state33==>" + state33.toString());
                    if (state33 == null || state33.getDevice() == null) {
                        break;
                    }

                    DeviceModel deviceModel = deviceDao.findOneByShortAddress(state33.getDevice());
                    if (deviceModel == null) {
                        break;
                    }
                    deviceModel.setDeviceId(state33.getDeviceId());
                    deviceModel.setProfileId(state33.getProfileId());
                    deviceModel.setDeviceVersion(state33.getDeviceVersion());
                    deviceModel.setEndpoint(state33.getEndpoint());
                    deviceResult = deviceDao.save(deviceModel);
                    if (deviceResult == null) {
                        break;
                    }
                    List<String> inClusters = state33.getInClusters();
                    List<DeviceClusterModel> oldClusterList = clusterDao.findByDeviceId(deviceModel.getId());
                    if (null != oldClusterList && oldClusterList.size() >0){
                        //说明已存过已设备的clusters
                        break;
                    }
                    inClusters.forEach(cluster -> {
                        clusterDao.save(DeviceClusterModel.builder()
                                .ieee(deviceModel.getIeee())
                                .deviceId(deviceModel.getId())
                                .cluster(cluster)
                                .inOrOut("in")
                                .build());
                    });

                    List<String> outClusters = state33.getOutClusters();
                    outClusters.forEach(cluster -> {
                        clusterDao.save(DeviceClusterModel.builder()
                                .ieee(deviceModel.getIeee())
                                .deviceId(deviceModel.getId())
                                .cluster(cluster)
                                .inOrOut("out")
                                .build());
                    });
                    this.notifyClientUsers(gatewayId, deviceResult,"device-new");
                    break;
            }


        }
    }


    public void processesSensorStatus(Long gatewayId,String payload) {

        //{"ZbReceived":{"0x8602":{"Device":"0x8602","0001/0100":0,"Endpoint":1,"LinkQuality":97}}}
        JSONObject jsonObject = JSON.parseObject(payload);

        if (!jsonObject.containsKey("ZbReceived")) {
            return;
        }
        JSONObject StatusItems = jsonObject.getJSONObject("ZbReceived");

        Iterator iterator = StatusItems.keySet().iterator();
        String key = null;
        while (iterator.hasNext()) {
            key = iterator.next().toString();
            JSONObject deviceStatus = StatusItems.getJSONObject(key);
            String deviceShortAddress = (String) deviceStatus.get("Device");
            String endpoint = String.valueOf(deviceStatus.get("Endpoint"));
            DeviceModel device = deviceDao.findOneByShortAddress(deviceShortAddress);
            if(null == device){
                continue;
            }

            deviceStatus.put("deviceId", device.getId());
            deviceStatus.entrySet().forEach(entry -> {
                String attributeName = entry.getKey();
                if(attributeName.equalsIgnoreCase("Device") || attributeName.equalsIgnoreCase("Endpoint")
                || attributeName.equalsIgnoreCase("deviceId")){
                    return;
                }
                String attributeValue = null;
                if (entry.getValue() instanceof String){
                    attributeValue = (String)entry.getValue();
                }else if(entry.getValue() instanceof Integer){
                    attributeValue = String.valueOf(entry.getValue());
                }

                if (attributeName.equalsIgnoreCase("Device") && attributeName.equalsIgnoreCase("Endpoint")) {
                    return;
                }
                DeviceAttributeStatusModel oldModel = deviceStatusAttributeRepository.findOneByDeviceIdAndClusterAttribute(device.getId(),attributeName);
                if(null != oldModel){
                    oldModel.setValue(attributeValue);
                    deviceStatusAttributeRepository.save(oldModel);
                }else{
                    DeviceAttributeStatusModel statusModel = DeviceAttributeStatusModel.builder()
                            .clusterAttribute(attributeName).value(attributeValue)
                            .endpoint(endpoint)
                            .ieee(device.getIeee())
                            .deviceId(device.getId())
                            .shortAddress(deviceShortAddress)
                            .build();
                    deviceStatusAttributeRepository.save(statusModel);
                }

                this.notifyClientUsers(gatewayId, deviceStatus,"device-status");

            });
            //HashMap<String, Object> data = ((JSONObject)StatusItems.get(key)).toJavaObject(new TypeReference<HashMap<String, Object>>(){});

        }//end while

    }

    private void processGatewayOffline(Long gatewayId, String payload) {
        if (payload.equalsIgnoreCase("offline")) {
            this.gatewayDeviceService.updateOnline(gatewayId, true);
            this.notifyClientUsers(gatewayId, "offline","gateway-status");
        } else {
            this.gatewayDeviceService.updateOnline(gatewayId, false);
            this.notifyClientUsers(gatewayId, "online","gateway-status");
        }

    }

    public void processGatewayStatus(Long gatewayId, GatewayStatusDto status) {
        gatewayDeviceService.updateStatus(gatewayId, status);
        this.notifyClientUsers(gatewayId, status,"gateway-status");
    }

    public boolean sendMQTTCommand(String targetGateway, String command, String payload) {
        mqttProxy.sendMsg("cmnd/" + targetGateway + "/" + command, payload);
        return true;
    }

    public boolean openDevicePairing(Long gatewayId) {
        GatewayDeviceModel gatewayInfo = this.gatewayDao.findById(gatewayId).orElse(null);
        String gatewayTopic = gatewayInfo.getLocationTopic();
        this.sendMQTTCommand(gatewayTopic, "ZbPermitJoin", "1");
        //DeviceModel deviceResult = deviceDao.findById(160L).orElse(null);
        //模拟库里任意一个设备进行添加。
        List<DeviceModel> devicesResult = deviceDao.findAll();
        DeviceModel deviceResult = devicesResult.get(0);
        this.notifyClientUsers(gatewayId, deviceResult,"device-new");
        return true;
    }

    public void notifyClientUsers(Long fromGatewayId, Object message,String messageType) {

        List<HouseUsersDto> targetUsers = houseService.queryUsersByGatewayId(fromGatewayId);
        //System.out.println("**********************Start to notify Clients-->" + String.valueOf(targetUsers.size()));

        Iterator iter = targetUsers.iterator();

        while (iter.hasNext()) {
            HouseUsersDto user = (HouseUsersDto) iter.next();
            this.NotifyIMServer(user.getToken(),message,messageType);
        }
    }
    private void NotifyIMServer(String targetUser, Object message, String messageType){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("target", targetUser);
        jsonObject.put("messageBody", message);
        jsonObject.put("messageType", messageType);
        //NotifyClientMessage msg = NotifyClientMessage.builder().targetClient(targetUser).messageType(messageType).message(messageBody).build();
        String msgString = jsonObject.toJSONString();
        //System.out.println("Source String ------------>" + msgString);
        this.mqProxy.sendMessage("WebSocketMessage",msgString);
    }
}
