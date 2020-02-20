package com.gps.api.config;

import com.gps.api.ws.MyWebSocketManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import com.gps.websocket.WebSocketManager;

@Configuration
public class WebSocketConfig {
    /**
     * @see https://www.cnblogs.com/betterboyz/p/8669879.html
     * 首先要注入ServerEndpointExporter，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。
     * 要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter，
     * 因为它将由容器自己提供和管理， 否则就会报重复的endpoint错误。
     */
    @ConditionalOnProperty(prefix = "server.websocket.exporter", name = "enable", havingValue = "true")
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    /**
     * 可以使用自己的WebSocketManager，注意名字是固定的
     */
    @Bean(WebSocketManager.WEBSOCKET_MANAGER_NAME)
    public WebSocketManager webSocketManager() {
        return new MyWebSocketManager();//继承自WebSocketManager
    }
}
