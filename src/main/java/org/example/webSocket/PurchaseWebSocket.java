package org.example.webSocket;

import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

/**
 * 功能描述：采购订单的websocket
 * 作者: Szy
 * 日期: 2023/4/9  8:05
 */
@ServerEndpoint("/websocket/purchaseSocket")
@Component
public class PurchaseWebSocket extends BaseWebSocket{

    public void sendStringMessage(String message) {
        try {
            for (BaseWebSocket socket: webSockets) {
                // synchronized (session) {
                // sock.session.getBasicRemote().sendText(message);    //异步会报错，这里用同步好
                if (socket instanceof PurchaseWebSocket)
                    socket.session.getAsyncRemote().sendText(message);
                //  }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
