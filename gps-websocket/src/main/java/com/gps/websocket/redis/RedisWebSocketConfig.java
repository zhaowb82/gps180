package com.gps.websocket.redis;

import com.gps.websocket.config.WebSocketHeartBeatChecker;
import com.gps.websocket.redis.action.ActionConfig;
import com.gps.websocket.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import com.gps.websocket.WebSocketManager;

import java.util.concurrent.CountDownLatch;

/**
 * @author xiongshiyan
 * redis管理websocket配置，利用redis的发布订阅功能实现，具备集群功能
 * 可以扩展此类，添加listener和topic及相应的receiver，使用自己的Enable注解导入即可
 * @see EnableRedisWebSocketManager
 */
@Configuration
@Import(ActionConfig.class)
public class RedisWebSocketConfig {
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean(WebSocketManager.WEBSOCKET_MANAGER_NAME)
    @ConditionalOnMissingBean(name = WebSocketManager.WEBSOCKET_MANAGER_NAME)
    public RedisWebSocketManager webSocketManager(@Autowired StringRedisTemplate stringRedisTemplate) {
        return new RedisWebSocketManager(stringRedisTemplate);
    }

    @Bean
    public CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    @Bean("receiver")
    public RedisReceiver receiver(
            @Autowired@Qualifier("latch") CountDownLatch latch) {
        return new DefaultRedisReceiver(latch);
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(@Qualifier("receiver") RedisReceiver receiver) {
        return new MessageListenerAdapter(receiver, RedisReceiver.RECEIVER_METHOD_NAME);
    }

    @Bean("redisMessageListenerContainer")
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(RedisWebSocketManager.CHANNEL));
        return container;
    }

    /**
     * applicationContext全局保存器
     */
    @Bean
    @ConditionalOnMissingBean
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }


    @Bean
    @ConditionalOnMissingBean
    public WebSocketHeartBeatChecker webSocketHeartBeatChecker(){
        return new WebSocketHeartBeatChecker();
    }
}