package com.gps.websocket.redis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xiongshiyan at 2018/10/15 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({RedisWebSocketConfig.class})
public @interface EnableRedisWebSocketManager {
}
