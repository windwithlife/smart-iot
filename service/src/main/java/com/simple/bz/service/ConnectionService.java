package com.simple.bz.service;

import com.alibaba.fastjson.JSONObject;
import com.simple.bz.dto.NotifyClientMessage;
import com.simple.common.redis.MessageQueueHandler;
import com.simple.common.redis.MessageQueueProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class ConnectionService implements MessageQueueHandler {
    private final MessageQueueProxy messageQueueProxy;
    private final WebSocketService webSocketService;

    private static final Map<String, Session> TOKEN_SESSION = new HashMap<>();
    private static final Map<String,String> SESSION_ID_TOKEN = new HashMap<>();
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");

    @PostConstruct
    public void refreshDate(){
        messageQueueProxy.registerMessageHandler("WebSocketMessage", this);
    }


    @Override
    public void onMessage(String topic, String message) {

        JSONObject msgObject = JSONObject.parseObject(message);
        String target = msgObject.getString("target");
        String msgBody = msgObject.getString("messageBody");
        System.out.println("Recv Message Topic -->" + topic + "Body---->" + msgBody);
        webSocketService.sendMessageToTarget(target,message);

    }
}
