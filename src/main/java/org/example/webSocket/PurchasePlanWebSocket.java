package org.example.webSocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 采购计划单 有变化后，给list页面发送消息
 * realWeight and realState
 */
@ServerEndpoint("/websocket/purchasePlan")
@Component
public class PurchasePlanWebSocket {
    static CopyOnWriteArraySet<PurchasePlanWebSocket> webSocket = new CopyOnWriteArraySet<>();
    Session session;

    @OnOpen
    public void onOpen(Session session) {
        webSocket.add(this);
        this.session = session;
        sendStringMessage("websocket connected!");
    }

    @OnMessage
    public void onMessage(String msg,Session session) {
        //log.info("来自客户端的消息:" + message);
        System.out.println(msg);

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    @OnClose
    public void onClose() {
        webSocket.remove(this);
    }

    public void sendStringMessage(String message) {
        try {
            synchronized (session) {
                session.getBasicRemote().sendText(message);    //异步会报错，这里用同步好
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object o){
        try {
            synchronized (session) {
                session.getBasicRemote().sendObject(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CopyOnWriteArraySet<PurchasePlanWebSocket> getWebSocket(){
        return webSocket;
    }


}
