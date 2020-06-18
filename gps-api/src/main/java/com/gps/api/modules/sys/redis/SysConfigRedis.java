/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.redis;


import com.gps.api.modules.sys.entity.SysConfigEntity;
import com.gps.api.common.utils.RedisKeys;
import com.gps.api.ApiRedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统配置Redis
 */
@Component
public class SysConfigRedis {
    @Autowired
    private ApiRedisUtils redisUtils;

    public void saveOrUpdate(SysConfigEntity config) {
        if(config == null){
            return ;
        }
        String key = RedisKeys.getSysConfigKey(config.getParamKey());
        redisUtils.set(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    public SysConfigEntity get(String configKey){
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, SysConfigEntity.class);
    }
}
