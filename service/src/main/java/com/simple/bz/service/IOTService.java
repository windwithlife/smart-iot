package com.simple.bz.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.bz.dto.DeviceStatusDto;
import com.simple.bz.dto.GatewayDeviceStatusDto;
import com.simple.bz.dto.DeviceDto;
import com.simple.bz.model.HouseUsersDto;
import com.simple.common.mqtt.MqttAdapter;
import com.simple.common.mqtt.MqttProxy;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class IOTService extends MqttAdapter {

    private final MqttProxy mqttProxy;
    private final HouseService houseService;
    private final WebSocketService webSocketService;

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
            System.out.println("Process in IOT service! Topic ===>" + topic +"Payload===>" +  payload);
            String[] params = StringUtils.split(topic,"/");
            if(params.length <=2){
                System.out.println("message is invalid!");
            }
            statusType = params[0];
            gatewayDevice = params[1];
            command = params[2];
            System.out.println("statusType==>" + statusType + "gatewayDevice==>" + gatewayDevice + "command ==>" + command );
        }

        if(statusType.equals("tele")){

            if(command.toUpperCase().equals("STATE")){
                GatewayDeviceStatusDto  dto = JSON.parseObject(payload, GatewayDeviceStatusDto.class);
                System.out.println("have parse gateway device status json object ===>");
                this.processGatewayState(gatewayDevice,dto);
                System.out.println(dto.toString());
            }
            if(command.equalsIgnoreCase("result")){
                GatewayDeviceStatusDto  dto = JSON.parseObject(payload, GatewayDeviceStatusDto.class);
                System.out.println("have parse gateway device status json object ===>");
                this.processGatewayState(gatewayDevice,dto);
                System.out.println(dto.toString());
            }
            if(command.toUpperCase().equals("LWT")){
                GatewayDeviceStatusDto  dto = GatewayDeviceStatusDto.builder().active(true).build();
                if (payload.equalsIgnoreCase("offline")){
                    dto.setActive(false);
                    System.out.println("gateway device will be offline");
                }else{
                    dto.setActive(true);
                    System.out.println("gateway device will be online");
                }

                this.processGatewayState(gatewayDevice,dto);
                System.out.println(dto.toString());
            }
            //传感器
            if(command.toUpperCase().equals("SENSOR")){
                JSONObject jsonObject = JSON.parseObject(payload);
                String statusName = "ZbState";
                JSONObject receivedObject = jsonObject.getJSONObject("ZbState");
                if (receivedObject.isEmpty()){
                    receivedObject = jsonObject.getJSONObject("ZbReceived");
                    statusName = "ZbReceived";
                }
                Set<String> keys = receivedObject.keySet();
                Iterator<String> it = keys.iterator();
                JSONObject resultObject;
                String key;
                while (it.hasNext()) {
                    key = it.next();
                    System.out.println("have parse device status json object key===>" + key);
                    resultObject = receivedObject.getJSONObject(key);
                    System.out.println("have parse device status json object value===>" + receivedObject.getString(key));
                    System.out.println("have parse device status json object value2===>" + resultObject.toJSONString());
                    DeviceStatusDto dto = resultObject.toJavaObject(DeviceStatusDto.class);
                    System.out.println("have parse device status json object ===>");
                    if(statusName.equalsIgnoreCase("ZbState")){
                        this.processDevicePairing(gatewayDevice,dto);
                    }
                    this.processSensorState(gatewayDevice,dto);
                    System.out.println(dto.toString());

                }

            }
        }

        if(statusType.toLowerCase().equals("stat")){
            System.out.println("Received Command Response");
            JSONObject jsonObject = JSON.parseObject(payload);
            Iterator<String> it = jsonObject.keySet().iterator();
            String key = null;
            while (it.hasNext()) {
                key = it.next();
            }
            if ((null != key) && key.toLowerCase().equals("zbstatus2")){
                JSONArray devices = jsonObject.getJSONArray(key);
                devices.forEach(a ->{
                    JSONObject deviceObject = (JSONObject) a;
                    DeviceDto device = deviceObject.toJavaObject(DeviceDto.class);
                    System.out.println("Devices info belong to gateway==>detail=======>");
                    System.out.println(device.toString());
                });

            }

        }

    }


    private void processDevicePairing(String gateway, DeviceStatusDto data){

        List<HouseUsersDto> targetUsers = houseService.queryUsersByGatewayName(gateway);

        targetUsers.forEach(user->{
            webSocketService.sendMessageToTarget(user.getToken(),data.toString());
        });


    }
    private void processSensorState(String gateway, DeviceStatusDto data){
        List<HouseUsersDto> targetUsers = houseService.queryUsersByGatewayName(gateway);
        targetUsers.forEach(user->{
            webSocketService.sendMessageToTarget(user.getToken(),data.toString());
        });


    }

    public void processGatewayState(String gateway,GatewayDeviceStatusDto status){
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
