
package com.gps.db.aspect;

import com.gps.db.exception.RRException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Redis切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Aspect
@Slf4j
@Configuration
public class GpsRedisAspect {

    //是否开启redis缓存  true开启   false关闭
    @Value("${spring.redis-gps.open: false}")
    private boolean open;

    @Around("execution(* com.gps.db.GpsRedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if (open) {
            try {
                result = point.proceed();
            } catch (Exception e) {
                log.error("redis error", e);
                throw new RRException("Redis服务异常");
            }
        }
        return result;
    }
}
