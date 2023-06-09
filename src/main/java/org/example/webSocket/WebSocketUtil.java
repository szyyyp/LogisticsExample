package org.example.webSocket;

import org.example.util.JsonUtils;

import java.util.concurrent.CopyOnWriteArraySet;

/*
    websocket 向客户端发送消息

    作者：szy
    日期：2022-6-10
 */
public class WebSocketUtil {

     /**
      * @param sentObj: 待发送的对象
     @param type:
        <li>0: 采购计划单内容有变化；</li>
     <li>1：仪表参数设置页面 系统参数；</li>
     <li>2: 仪表参数设置页面 传感器参数；</li>

     */
    public static String generateMsg(Object sentObj, int type){
        WebSocketMessage msg = new WebSocketMessage();
        msg.setMsgType(type);
        msg.setObj(sentObj);
        return JsonUtils.toJson(msg);
    }

}
