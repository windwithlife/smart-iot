package com.simple.common.redis;

public class RedisSubscriber implements MessageQueueHandler {
    @Override
    public void onMessage(String topic, String message) {
        System.out.println("Topic --->" + topic);
        System.out.println("MessageBody --->" + message);
    }

}
