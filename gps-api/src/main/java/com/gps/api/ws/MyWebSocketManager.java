package com.gps.api.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.gps.websocket.WebSocket;
import com.gps.websocket.memory.MemWebSocketManager;

import javax.websocket.Session;
import java.util.List;

@Slf4j
public class MyWebSocketManager extends MemWebSocketManager {
    @Autowired
    private WebSocketPush webSocketPush;

    @Override
    public void onMessage(String identifier, String message, Session session) {

    }

    @Override
    public void doRemoveSocket(List<WebSocket> webSocket) {
        webSocketPush.doRemoveSocket(webSocket);
    }
}
