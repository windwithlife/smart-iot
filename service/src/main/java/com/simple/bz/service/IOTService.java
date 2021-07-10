package com.simple.bz.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.simple.bz.dao.DeviceClusterRepository;
import com.simple.bz.dao.DeviceRepository;
import com.simple.bz.dao.DeviceStatusAttributeRepository;
import com.simple.bz.dto.*;
import com.simple.bz.model.DeviceClusterModel;
import com.simple.bz.model.DeviceModel;
import com.simple.bz.model.HouseUsersDto;
import com.simple.bz.model.StatusAttributeModel;
import com.simple.common.error.ServiceException;
import com.simple.common.mqtt.MqttAdapter;
import com.simple.common.mqtt.MqttProxy;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class IOTService extends MqttAdapter {

    private final MqttProxy mqttProxy;
    private final HouseService houseService;
    private final WebSocketService webSocketService;
    private final DeviceService deviceService;
    private final DeviceRepository deviceDao;
    private final DeviceClusterRepository clusterDao;
    private final DeviceStatusAttributeRepository deviceStatusAttributeRepository;

    @PostConstruct
    private void init() {

        mqttProxy.registerHandler("tele/",this);
        mqttProxy.registerHandler("stat/",this);

    }

    @Override
    public void handleMessage(String topic, String payload) {


        String statusType = "tele";
        String gatewayDevice = "";
        String command = "";
        if((!topic.startsWith("tele/")) && (!topic.startsWith("stat"))){
            //System.out.println("Not IOT Message !");
            return;
        }else{
            //System.out.println("Process in IOT service! start!" );
            System.out.println("Process in IOT service! Topic ===>【" + topic +"】 Payload===>" +  payload);
            String[] params = StringUtils.split(topic,"/");
            if(params.length <=2){
                System.out.println("message is invalid!");
            }
            statusType = params[0];
            gatewayDevice = params[1];
            command = params[2];
            System.out.println("statusType==>【" + statusType + "】 gatewayDevice==>【" + gatewayDevice + "】 command ==>" + command );
        }
        String topicFormat = String.format("%s/#/%s",statusType,command);
        switch (topicFormat){
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
                this.processDevicePairing(gatewayDevice,payload);
                break;
            case "tele/#/STATE":
                //STATE = {"Time":"2021-06-19T00:37:17","Uptime":"0T00:00:09","UptimeSec":9,"Vcc":3.423,"Heap":27,"SleepMode":"Dynamic","Sleep":50,"LoadAvg":19,"MqttCount":1,"Wifi":{"AP":1,"SSId":"********","BSSId":"BC:2E:F6:DC:39:64","Channel":6,"RSSI":62,"Signal":-69,"LinkCount":1,"Downtime":"0T00:00:03"}}
                GatewayStatusDto dto = JSON.parseObject(payload, GatewayStatusDto.class);
                System.out.println("have parse gateway device status json object ===>" + dto.toString());
                this.processGatewayState(gatewayDevice,dto);
            case "tele/#/LWT":
                GatewayStatusDto dto = GatewayStatusDto.builder().active(true).build();
                if (payload.equalsIgnoreCase("offline")){
                    dto.setActive(false);
                    System.out.println("gateway device will be offline");
                }else{
                    dto.setActive(true);
                    System.out.println("gateway device will be online");
                }
                this.processGatewayState(gatewayDevice,dto);
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
                this.proccessSensorStatus(gatewayDevice,payload);
                break;
        }


    }


    private void processDevicePairing(String gateway, NewDeviceResponse data){
        JSONObject jsonObject = JSON.parseObject(payload);
        if(jsonObject.get("ZbState") instanceof JSONObject &&
                ((JSONObject)jsonObject.get("ZbState")).containsKey("Status")){

            int statusCode = ((JSONObject)jsonObject.get("ZbState")).getInteger("Status");
            DeviceModel deviceResult = null;
            JSONObject stateJsonObject = (JSONObject)jsonObject.get("ZbState");
            switch (statusCode) {
                case 30:
                    //有设备连接上网关，插入设备表
                    //{"ZbState":{"Status":30,"IEEEAddr":"0x000D6FFFFED209BF","ShortAddr":"0x7984","PowerSource":false,"ReceiveWhenIdle":false,"Security":false}}
                    DeviceState30 state30 = stateJsonObject.toJavaObject(DeviceState30.class);

                    if(state30 != null) {
                        DeviceModel device = DeviceModel.builder().ieee(state30.getIEEEAddr()).shortAddress(state30.getShortAddr()).powerSource(state30.getPowerSource())
                                .receiveWhenIdle(state30.getReceiveWhenIdle()).security(state30.getSecurity()).build();
                        deviceResult = deviceDao.save(device);
                    }
                    break;
                case 33:
                    //Received the simple Descriptor with active ZCL clusters for a Zigbee device
                    // {"ZbState":{"Status":33,"Device":"0x7984","Endpoint":"0x01","ProfileId":"0x0104","DeviceId":"0x0402","DeviceVersion":0,"InClusters":
                    DeviceState33 state33 = stateJsonObject.toJavaObject(DeviceState33.class);
                    if(state33 != null && state33.getDevice() != null) {
                        DeviceModel deviceModel = deviceDao.findOneByShortAddress(state33.getDevice());
                        if(deviceModel != null){
                            deviceModel.setDeviceId(state33.getDeviceId());
                            deviceModel.setProfileId(state33.getProfileId());
                            deviceModel.setDeviceVersion(state33.getDeviceVersion());
                            deviceModel.setEndpoint(state33.getEndpoint());
                            deviceResult = deviceDao.save(deviceModel);
                            List<String> inClusters = state33.getInClusters();
                            inClusters.forEach(cluster ->{
                              clusterDao.save(DeviceClusterModel.builder()
                                      .ieee(deviceModel.getIeee())
                                      .deviceId(deviceModel.getId())
                                      .cluster(cluster)
                                      .inOrOut("in")
                                      .build());
                            });

                            List<String> outClusters = state33.getOutClusters();
                            inClusters.forEach(cluster ->{
                                clusterDao.save(DeviceClusterModel.builder()
                                        .ieee(deviceModel.getIeee())
                                        .deviceId(deviceModel.getId())
                                        .cluster(cluster)
                                        .inOrOut("out")
                                        .build());
                            });

                        }

                    }
                    break;
            }

        List<HouseUsersDto> targetUsers = houseService.queryUsersByGatewayName(gateway);
        targetUsers.forEach(user->{
            webSocketService.sendMessageToTarget(user.getToken(),deviceResult.toString());
        });


    }
        public void proccessSensorStatus(String topic ,String payload){

            //{"ZbReceived":{"0x8602":{"Device":"0x8602","0001/0100":0,"Endpoint":1,"LinkQuality":97}}}
            JSONObject jsonObject = JSON.parseObject(payload);

            if(!jsonObject.containsKey("ZbReceived")){
                return;
            }
            JSONObject StatusItems = jsonObject.getJSONObject("ZbReceived");

            Iterator iterator = StatusItems.keySet().iterator();
            String key = null;
            while (iterator.hasNext()){
                key = iterator.next().toString();
                JSONObject deviceStatus = StatusItems.getJSONObject(key);
                String deviceShortAddress = (String)deviceStatus.get("Device");
                String endpoint           = (String)deviceStatus.get("Endpoint");
                DeviceModel device = deviceDao.findOneByShortAddress(deviceShortAddress);
                deviceStatus.entrySet().forEach( entry ->{

                    String attributeName = entry.getKey();
                    String attributeValue = (String)entry.getValue();
                    if(attributeName.equalsIgnoreCase("Device") && attributeName.equalsIgnoreCase("Endpoint")){
                        return;
                    }
                    StatusAttributeModel statusModel = StatusAttributeModel.builder()
                            .clusterAttribute(attributeName).value(attributeValue)
                            .endpoint(endpoint)
                            .ieee(device.getIeee())
                            .deviceId(device.getId())
                            .build()
                    deviceStatusAttributeRepository.save(statusModel);

                });
                //HashMap<String, Object> data = ((JSONObject)StatusItems.get(key)).toJavaObject(new TypeReference<HashMap<String, Object>>(){});

            }//end while

    }
    private void processSensorState(String gateway, DeviceStatusDto data){
        List<HouseUsersDto> targetUsers = houseService.queryUsersByGatewayName(gateway);
        targetUsers.forEach(user->{
            webSocketService.sendMessageToTarget(user.getToken(),data.toString());
        });


    }

    public void processGatewayState(String gateway, GatewayStatusDto status){
        List<HouseUsersDto> targetUsers = houseService.queryUsersByGatewayName(gateway);
        targetUsers.forEach(user->{
            webSocketService.sendMessageToTarget(user.getToken(),status.toString());
        });
    }

    public boolean sendMQTTCommand(String targetGateway, String command, String payload){
        mqttProxy.sendMsg("cmnd/" + targetGateway + "/" + command, payload);
        return true;
    }
    public boolean openDevicePairing(String gateway){
        return this.sendMQTTCommand(gateway,"ZbPermitJoin", "1");
    }
}
