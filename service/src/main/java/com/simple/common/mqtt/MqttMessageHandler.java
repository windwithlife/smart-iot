package com.simple.common.mqtt;

public interface MqttMessageHandler {

//    public void setProxy(MqttProxy proxy);
//    public MqttProxy getProxy();
    public void handleMessage(String topic,String payload);
}
