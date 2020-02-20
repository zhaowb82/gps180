/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 * <p>
 * https://www.gps180.com
 * <p>
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.redis;


import com.gps.api.modules.sys.entity.SysUserTokenEntity;
import com.gps.api.ApiRedisUtils;
import com.gps.api.common.utils.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统配置Redis
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class SysUserTokenRedis {
    @Autowired
    private ApiRedisUtils redisUtils;

    public void saveOrUpdate(SysUserTokenEntity token) {
        if (token == null) {
            return;
        }
        String key = RedisKeys.getSysUserTokenKey(token.getId() + "");
        redisUtils.set(key, token, 3600);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysUserTokenKey(configKey);
        redisUtils.delete(key);
    }

    public SysUserTokenEntity get(String configKey) {
        String key = RedisKeys.getSysUserTokenKey(configKey);
        return redisUtils.get(key, SysUserTokenEntity.class);
    }
}
