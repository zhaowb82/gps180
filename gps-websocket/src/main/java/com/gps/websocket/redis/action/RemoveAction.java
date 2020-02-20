package com.gps.websocket.redis.action;

import com.alibaba.fastjson.JSONObject;
import com.gps.websocket.WebSocket;
import com.gps.websocket.WebSocketManager;

import java.util.Map;

/**
 * {
 * "action":"remove",
 * "identifier":"xxx"
 * }
 * 给webSocket发送消息的action
 *
 * @author xiongshiyan at 2018/10/12 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class RemoveAction implements Action {
    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(IDENTIFIER)) {
            return;
        }

        String identifier = object.getString(IDENTIFIER);

        Map<String, WebSocket> localWebSocketMap = manager.localWebSocketMap();
        if (localWebSocketMap.containsKey(identifier)) {
            localWebSocketMap.remove(identifier);
        }
    }
}
