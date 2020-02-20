package com.gps.websocket;

import lombok.Data;

import javax.websocket.Session;
import java.util.Date;

/**
 * @author xiongshiyan at 2018/10/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Data
public class WebSocket {
    /**
     * 代表一个连接
     */
    private Session session;

    /**
     * 唯一标识
     */
    private String identifier;
    /**
     * 最后心跳时间
     */
    private Date lastHeart;

}
