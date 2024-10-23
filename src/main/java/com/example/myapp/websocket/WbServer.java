package com.example.myapp.websocket;

import org.springframework.stereotype.Component;

import com.example.myapp.utils.SessionUtils;

import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import java.io.IOException;
@ServerEndpoint(
    value = "/wb" , 
    configurator = WbServerConfig.class
    )
@Component
public class WbServer {
    public WbServer() {
        //每当有一个连接，都会执行一次构造方法
        System.out.println("新的连接。。。");
    }
    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>();
    public void sendMessage(Session toSession, String message) {
        if (toSession != null) {
            try {
                toSession.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("对方不在线");
        }
    }
    public void sendMessageToUser(String user_id, String message) {
        System.out.println(user_id);
        Session toSession = SESSIONS.get(user_id);
        sendMessage(toSession, message);
        System.out.println(message);
    }
        @OnOpen
    public void onOpen(Session session , EndpointConfig conf) {
        HandshakeRequest req =(HandshakeRequest) conf.getUserProperties().get("handshakereq") ; 
        String user_id = SessionUtils.readSession("user_id", (HttpSession)req.getHttpSession()) ;
        if (SESSIONS.get(user_id) != null) {
            if(SESSIONS.get(user_id).isOpen())
            {
                System.out.println("已经上线过了");
                return;
            }
        }
        
        SESSIONS.put(user_id.trim(), session);
    }
    
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        SESSIONS.remove(userId);
    }
        @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }
    @OnMessage
    public void onMessage(String message) {
        System.out.println("服务器收到消息：" + message);
    }
}
