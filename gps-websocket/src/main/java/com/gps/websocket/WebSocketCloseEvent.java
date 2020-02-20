package com.gps.websocket;

import org.springframework.context.ApplicationEvent;

/**
 * @author xiongshiyan at 2018/11/6 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class WebSocketCloseEvent extends ApplicationEvent {
    public WebSocketCloseEvent(WebSocket webSocket){
        super(webSocket);
    }
}
