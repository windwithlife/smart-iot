package com.simple.bz.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simple.bz.dto.NotifyClientMessage;
import com.simple.common.auth.Sessions;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@ServerEndpoint("/webSocket/{username}")//encoders = { ServerEncoder.class },可选值，指定编码转换器，传输对象时用到,这里直接转json就ok
public class WebSocketService {


    private static final Map<String, Session> TOKEN_SESSION = new HashMap<>();
    private static final Map<String,String> SESSION_ID_TOKEN = new HashMap<>();
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");

    @PostConstruct
    public void refreshDate(){

    }

    @OnOpen
    public void onOpen(Session session,@PathParam("username")String username){
        System.out.println("新的连接进来了！ SessionID==>" + session.getId() + "UserId===>" + username.substring(0,20));
        if (username == null){
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT,"username参数为空"));
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage());
            }
        }else {
            try{

                //System.out.println("Current Connection userId ==> " + Sessions.getSessionUserInfo(username).getUserId());
                //System.out.println("Current Connection sessionId ==> " + session.getId());
                SESSION_ID_TOKEN.put(session.getId(),username);
                TOKEN_SESSION.put(username,session);
                Session sessionNew = TOKEN_SESSION.get(username);
                if(null == sessionNew){
                    System.out.println("此用户没有上线");
                }
                System.out.println(username);
            }catch (Exception e) {
                e.printStackTrace();
            }
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

        try {
            Session session = TOKEN_SESSION.get(token);
            if(null == session){
                //System.out.println("此用户没有上线 username==>" + token + "session count ==>" + String.valueOf(TOKEN_SESSION.size()));
                //System.out.println("当前用户Session Detail" + TOKEN_SESSION.toString());
                System.out.println("此用户没有上线");
                return;
            }
            session.getBasicRemote().sendText((String)t);
            //System.out.println("发送指定token===> token:[" + token + "]");
            System.out.println("发送指定token===> token:[" + token.substring(0,20) + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnError
    public void onError(Throwable throwable) {
        System.out.println("Connection is disconnected by gateway!");
        //throwable.printStackTrace();
    }

//    @Override
//    public void onMessage(String topic, String message) {
//        System.out.println("message body=====>"  + message);
//        NotifyClientMessage msg = JSONObject.parseObject(message,NotifyClientMessage.class);
//        System.out.println("Recv Message Topic -->" + topic + "Body---->" + msg.toString());
//        this.sendMessageToTarget(msg.getTargetClient(),msg.toString());
//        //System.out.println("Recv Message Topic -->" + topic + "Body---->" + msg.toString());
//    }
}
