package org.example.webSocket;

import lombok.Data;

@Data
public class WebSocketMessage implements java.io.Serializable   {
    /*
        0: 仪表参数设置页面所有参数；
        1：仪表参数设置页面 系统参数；
        2: 仪表参数设置页面 传感器参数；

        10：仪表实时重量
        11：仪表实时状态
     */
    int msgType;

    Object obj = null;

}
