package com.gps.websocket;

import java.util.List;

/**
 * @author xiongshiyan at 2019/3/20 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@FunctionalInterface
public interface TodoAtRemoved {
    /**
     * 在删除的时候额外要干什么
     * @param webSockets  webSockets
     */
    void todoWith(List<WebSocket> webSockets);
}
