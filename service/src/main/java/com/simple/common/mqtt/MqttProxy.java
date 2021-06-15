package com.simple.common.mqtt;

import com.alibaba.fastjson.JSON;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.simple.JsonUtils;
import com.simple.bz.dto.*;
import com.simple.common.error.ServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class MqttProxy {

    static ILogger logger = SLoggerFactory.getLogger(MqttProxy.class);

    private final MqttGateway mqttGateway;
    private final ServiceHelper serviceHelper;
    private HashMap<String, MqttMessageHandler> handlerMap = new HashMap<>();


    public void handleMessage(String topic, String payload) {

        //System.out.println("MQTT Service Received topic: " + topic);
        //System.out.println("MQTT Service Received payload: " + payload);
        Set<String> keys = handlerMap.keySet();
        Iterator<String> it = keys.iterator();
        String key;
        while (it.hasNext()) {
            key = it.next();
            //System.out.println(key+":"+handlerMap.get(key).toString());
            MqttMessageHandler handler = handlerMap.get(key);
            handler.handleMessage(topic, payload);
        }
//        if (!JsonUtils.isJson(payload)) {
//            System.out.println("Invalid String Message ! Received payload: " + payload);
//            return;
//        }
//        MqttRequest request = JSON.parseObject(payload, MqttRequest.class);
//        if ((null == request) || null == request.getCommand()) {
//            return;
//        }
//
//        Set<String> keys = handlerMap.keySet();
//        Iterator<String> it = keys.iterator();
//        String key;
//        while (it.hasNext()) {
//            key = it.next();
//            System.out.println(key+":"+handlerMap.get(key).toString());
//            MqttMessageHandler handler = handlerMap.get(key);
//            handler.handleMessage(request);
//        }

    }
    public void registerHandler(String command, MqttMessageHandler handler){
        //handler.setProxy(this);
        this.handlerMap.put(command, handler);
    }

    public void sendMsg(String topic, String command , Object dto){
        MqttDto mqttDto = MqttDto.builder().command(command).build();
        mqttDto.setCommand(command);
        mqttDto.setParamObject(dto);
        this.sendMsg(topic, mqttDto.toString());
    }
    public  void sendMsg(String topic, String payload){
        mqttGateway.sendToMqtt(topic,payload);
    }

}