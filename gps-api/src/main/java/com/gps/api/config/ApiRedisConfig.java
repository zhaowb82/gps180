
package com.gps.api.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ObjectUtils;

import java.time.Duration;

/**
 * Redis配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
//@EnableCaching
public class ApiRedisConfig {
//    @Autowired
//    private RedisConnectionFactory factory;

    @Bean
    @Primary
//    @ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
    public GenericObjectPoolConfig apiRedisPool2(
            @Value("${spring.redis.lettuce.pool.max-active}") int maxActive,
            @Value("${spring.redis.lettuce.pool.max-wait}") int maxWait,
            @Value("${spring.redis.lettuce.pool.max-idle}") int maxIdle,
            @Value("${spring.redis.lettuce.pool.min-idle}") int minIdle
    ) {
//        return new GenericObjectPoolConfig<>();
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        return genericObjectPoolConfig;
    }

    @Bean
    public RedisStandaloneConfiguration apiRedisConfig1(
            @Value("${spring.redis.database}") int dbIndex,
            @Value("${spring.redis.host}") String host,
            @Value("${spring.redis.port}") int port,
            @Value("${spring.redis.password}") String password
    ) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setDatabase(dbIndex);
        if (!ObjectUtils.isEmpty(password)) {
            RedisPassword redisPassword = RedisPassword.of(password);
            configuration.setPassword(redisPassword);
        }
        return configuration;
    }

    @Bean("apiConnFactory")
    @Primary
    public LettuceConnectionFactory factory(@Qualifier("apiRedisPool2") GenericObjectPoolConfig poolConfig,
                                            @Qualifier("apiRedisConfig1") RedisStandaloneConfiguration configuration) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();
        return new LettuceConnectionFactory(configuration, clientConfiguration);
    }

    @Bean(name = "apiRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory apiConnFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(apiConnFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "apiHashOperations")
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> apiRedisTemplate) {
        return apiRedisTemplate.opsForHash();
    }

    @Bean(name = "apiValueOperations")
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> apiRedisTemplate) {
        return apiRedisTemplate.opsForValue();
    }

    @Bean(name = "apiListOperations")
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> apiRedisTemplate) {
        return apiRedisTemplate.opsForList();
    }

    @Bean(name = "apiSetOperations")
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> apiRedisTemplate) {
        return apiRedisTemplate.opsForSet();
    }

    @Bean(name = "apiZSetOperations")
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> apiRedisTemplate) {
        return apiRedisTemplate.opsForZSet();
    }
}
