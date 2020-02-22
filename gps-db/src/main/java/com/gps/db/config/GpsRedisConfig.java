
package com.gps.db.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ObjectUtils;

/**
 * Redis配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class GpsRedisConfig {
//    @Autowired
//    private RedisConnectionFactory gpsConnFactory;

    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
    public GenericObjectPoolConfig<?> gpsRedisPool1(
            @Value("${spring.redis-gps.lettuce.pool.max-active}") int maxActive,
            @Value("${spring.redis-gps.lettuce.pool.max-wait}") int maxWait,
            @Value("${spring.redis-gps.lettuce.pool.max-idle}") int maxIdle,
            @Value("${spring.redis-gps.lettuce.pool.min-idle}") int minIdle
    ) {
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        return genericObjectPoolConfig;
    }

    @Bean
    public RedisStandaloneConfiguration gpsRedisConfig1(
            @Value("${spring.redis-gps.database}") int dbIndex,
            @Value("${spring.redis-gps.host}") String host,
            @Value("${spring.redis-gps.port}") int port,
            @Value("${spring.redis-gps.password}") String password
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

    @Bean("gpsConnFactory")
    public LettuceConnectionFactory factory(@Qualifier("gpsRedisPool1") GenericObjectPoolConfig gpsRedisPool1,
                                            @Qualifier("gpsRedisConfig1") RedisStandaloneConfiguration gpsRedisConfig1) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(gpsRedisPool1).build();
        return new LettuceConnectionFactory(gpsRedisConfig1, clientConfiguration);
    }

    @Bean(name = "gpsRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("gpsConnFactory") RedisConnectionFactory gpsConnFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(gpsConnFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "gpsHashOperations")
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> gpsRedisTemplate) {
        return gpsRedisTemplate.opsForHash();
    }

    @Bean(name = "gpsValueOperations")
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> gpsRedisTemplate) {
        return gpsRedisTemplate.opsForValue();
    }

    @Bean(name = "gpsListOperations")
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> gpsRedisTemplate) {
        return gpsRedisTemplate.opsForList();
    }

    @Bean(name = "gpsSetOperations")
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> gpsRedisTemplate) {
        return gpsRedisTemplate.opsForSet();
    }

    @Bean(name = "gpsZSetOperations")
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> gpsRedisTemplate) {
        return gpsRedisTemplate.opsForZSet();
    }

    @Bean
    public RedisTemplate<String, Integer> intRedisTemplate(RedisConnectionFactory gpsConnFactory) {
        RedisTemplate<String, Integer> template = new RedisTemplate<>();
        template.setConnectionFactory(gpsConnFactory);
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setDefaultSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
