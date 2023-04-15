package org.example.webSocket;

import javax.websocket.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 功能描述：
 * 作者: Szy
 * 日期: 2023/4/9  8:07
 */
public abstract class BaseWebSocket  {
    static CopyOnWriteArraySet<BaseWebSocket> webSockets = new CopyOnWriteArraySet<>();
    Session session;


    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
       // sendStringMessage("websocket connected!");
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
        //todo
        webSockets.remove(this);
    }


}
