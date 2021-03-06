package com.simple.bz.service;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Component
@ServerEndpoint("/webSocket/{username}")//encoders = { ServerEncoder.class },可选值，指定编码转换器，传输对象时用到,这里直接转json就ok
public class WebSocketService {

    private static final Map<String, Session> TOKEN_SESSION = new HashMap<>();
    private static final Map<String,String> SESSION_ID_TOKEN = new HashMap<>();
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");

    @PostConstruct
    public void refreshDate(){
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(()->{
            sendMessage(FORMAT.format(new Date()));
        },1000,1000, TimeUnit.MILLISECONDS);
    }
    @OnOpen
    public void onOpen(Session session,@PathParam("username")String username){
        System.out.println("新的连接进来了"+session.getId());
        if (username == null){
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT,"username参数为空"));
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage());
            }
        }else {
            SESSION_ID_TOKEN.put(session.getId(),username);
            TOKEN_SESSION.put(username,session);
            System.out.println(username);
        }
    }
    @OnClose
    public void onColse(Session session){
        System.out.println("断开连接"+session.getId());
        String username = SESSION_ID_TOKEN.get(session.getId());
        TOKEN_SESSION.remove(username);
        SESSION_ID_TOKEN.remove(session.getId());

    }
    @OnMessage
    public void onMessage(Session session,String message){
        System.out.println("收到客户端发来的消息:"+message+session.getId());
        sendMessageToTarget(SESSION_ID_TOKEN.get(session.getId()),message+"已收到");
    }
    public void sendMessage(String message){

        TOKEN_SESSION.values().forEach((session)->{
            if (session != null && session.isOpen()) {
                String user = SESSION_ID_TOKEN.get(session.getId());
                session.getAsyncRemote().sendText("To User===>" + message);
            }

        });
    }
    public <T> void sendMessageToTarget(String token,Object t){
        System.out.println("发送指定token消息");
        try {
            TOKEN_SESSION.get(token).getBasicRemote().sendText((String)t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }
}
