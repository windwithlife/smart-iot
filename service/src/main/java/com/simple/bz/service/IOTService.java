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
                this.processGatewayState(topic,payload);
                System.out.println(dto.toString());
            }
            //传感器
            if(command.toUpperCase().equals("SENSOR")){
                JSONObject jsonObject = JSON.parseObject(payload);
                JSONObject receivedObject = jsonObject.getJSONObject("ZbReceived");
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

    private void processSensorState(String gateway, DeviceStatusDto data){
        List<HouseUsersDto> targetUsers = houseService.queryUsersByGatewayName(gateway);
        targetUsers.forEach(user->{
            webSocketService.sendMessageToTarget(user.getToken(),data.toString());
        });


    }

    public void processGatewayState(String topic,String payload){

    }

}
