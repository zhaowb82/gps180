package com.gps.websocket.memory;

import com.gps.websocket.WebSocket;
import com.gps.websocket.WebSocketCloseEvent;
import com.gps.websocket.WebSocketConnectEvent;
import com.gps.websocket.WebSocketManager;
import com.gps.websocket.utils.SpringContextHolder;
import com.gps.websocket.utils.WebSocketUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiongshiyan at 2018/10/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class MemWebSocketManager implements WebSocketManager {
    /**
     * 因为全局只有一个 WebSocketManager ，所以才敢定义为非static
     */
    private final Map<String, WebSocket> connections = new ConcurrentHashMap<>(100);

    @Override
    public WebSocket get(String identifier) {
        return connections.get(identifier);
    }

    @Override
    public void put(String identifier, WebSocket webSocket) {
        connections.put(identifier, webSocket);
        //发送连接事件
        SpringContextHolder.getApplicationContext().publishEvent(new WebSocketConnectEvent(webSocket));
    }

    @Override
    public void remove(String identifier) {
        WebSocket removedWebSocket = connections.remove(identifier);
        //发送关闭事件
        if (null != removedWebSocket) {
            SpringContextHolder.getApplicationContext().publishEvent(new WebSocketCloseEvent(removedWebSocket));
        }
    }


    @Override
    public Map<String, WebSocket> localWebSocketMap() {
        return connections;
    }

    @Override
    public void sendMessage(String identifier, String message) {
        WebSocket webSocket = get(identifier);
        if (null == webSocket) {
            throw new RuntimeException("identifier 不存在");
        }

        WebSocketUtil.sendMessage(webSocket.getSession(), message);
    }

    @Override
    public void broadcast(String message) {
        localWebSocketMap().values().forEach(
                webSocket -> WebSocketUtil.sendMessage(webSocket.getSession(), message));
    }

    @Override
    public void onMessage(String identifier, String message) {

    }

    @Override
    public void doRemoveSocket(List<WebSocket> webSocket) {

    }
}
