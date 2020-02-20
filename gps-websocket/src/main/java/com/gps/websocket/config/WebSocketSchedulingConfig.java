package com.gps.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.gps.websocket.WebSocketManager;


/**
 * 只需要配置一下三个选项即可开启心跳监测
 *   webSocket.heartCheck.enabled=true
     webSocket.heartCheck.timeSpan=1000
     webSocket.heartCheck.errorToleration=30
 * @author xiongshiyan
 * 这是一个配置模板
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "webSocket.heartCheck" , name = "enabled" , havingValue = "true")
public class WebSocketSchedulingConfig {

    @Value("${webSocket.heartCheck.timeSpan:10000}")
    private long timeSpan;
    @Value("${webSocket.heartCheck.errorToleration:30}")
    private int errorToleration;

    @Autowired
    private WebSocketManager webSocketManager;
    @Autowired
    private WebSocketHeartBeatChecker webSocketHeartBeatChecker;
    /**
     * 定时检测 WebSocket 的心跳
     */
    @Scheduled(cron = "${webSocket.heartCheck.trigger}")
    public void webSocketHeartCheckJob() {
        webSocketHeartBeatChecker.check(webSocketManager , timeSpan , errorToleration , (webSocket)->{
            //数据库操作...
            webSocketManager.doRemoveSocket(webSocket);
        });
    }

}
