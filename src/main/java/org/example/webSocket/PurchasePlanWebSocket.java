package org.example.webSocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 采购计划单 有变化后，给list页面发送消息
 * realWeight and realState
 */
@ServerEndpoint("/websocket/purchasePlanSocket")
@Component
public class PurchasePlanWebSocket extends BaseWebSocket {

}
