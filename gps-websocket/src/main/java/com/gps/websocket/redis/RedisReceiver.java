package com.gps.websocket.redis;

/**
 * redis 接收器接口,主要目的是固定接口名字
 * @author xiongshiyan
 */
public interface RedisReceiver {
    String RECEIVER_METHOD_NAME = "receiveMessage";
    /**
     * 回调方法
     * @param message 接收到的消息
     */
    void receiveMessage(String message);
}
