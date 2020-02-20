package com.gps.websocket.redis;

import com.alibaba.fastjson.JSONObject;
import com.gps.websocket.redis.action.Action;
import com.gps.websocket.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import com.gps.websocket.WebSocketManager;

import java.util.concurrent.CountDownLatch;

/**
 * redis消息订阅者
 *
 * @author xiongshiyan
 */
@Slf4j
public class DefaultRedisReceiver implements RedisReceiver {

    private CountDownLatch latch;

    public DefaultRedisReceiver(CountDownLatch latch) {
        this.latch = latch;
    }

    /**
     * 此方法会被反射调用
     */
    @Override
    public void receiveMessage(String message) {
        log.info(message);

        JSONObject object = JSONObject.parseObject(message);
        if (!object.containsKey(Action.ACTION)) {
            return;
        }
        String actionName = object.getString(Action.ACTION);
        Action action = getAction(actionName);
        action.doMessage(getWebSocketManager(), object);

        latch.countDown();
    }

    private Action getAction(String actionName) {
        boolean containsBean = SpringContextHolder.getApplicationContext().containsBean(actionName);
        if (!containsBean) {
            throw new RuntimeException("容器中不存在处理这个请求 " + actionName + " 的Action，请确保正确注入了");
        }
        return SpringContextHolder.getBean(actionName, Action.class);
    }

    protected WebSocketManager getWebSocketManager() {
        boolean containsBean = SpringContextHolder.getApplicationContext().containsBean(WebSocketManager.WEBSOCKET_MANAGER_NAME);
        if (!containsBean) {
            throw new RuntimeException("容器中不存在WebSocketManager，请确保正确注入webSocketManger");
        }
        return SpringContextHolder.getBean(WebSocketManager.WEBSOCKET_MANAGER_NAME, WebSocketManager.class);
    }
}