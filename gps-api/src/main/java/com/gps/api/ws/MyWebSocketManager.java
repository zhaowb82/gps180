package com.gps.api.ws;

import org.springframework.beans.factory.annotation.Autowired;
import com.gps.websocket.WebSocket;
import com.gps.websocket.memory.MemWebSocketManager;

import java.util.List;

public class MyWebSocketManager extends MemWebSocketManager {
    @Autowired
    private WebSocketPush webSocketPush;

    @Override
    public void onMessage(String identifier, String message) {

    }

    @Override
    public void doRemoveSocket(List<WebSocket> webSocket) {
        webSocketPush.doRemoveSocket(webSocket);
    }
}
