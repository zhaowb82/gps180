/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 * <p>
 * https://www.gps180.com
 * <p>
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gps.db.entity.DeviceEntity;
import com.gps.db.utils.R;
import com.gps.api.common.utils.ShiroUtils;
import com.gps.api.common.utils.JwtUtils;
import com.gps.api.modules.sys.dao.SysUserTokenDao;
import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.api.modules.sys.entity.SysUserTokenEntity;
//import TokenGenerator;
import com.gps.api.modules.sys.redis.SysUserTokenRedis;
import com.gps.api.modules.sys.service.SysUserService;
import com.gps.api.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    //12小时后过期
//	private final static int EXPIRE = 3600 * 12;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserTokenRedis sysUserTokenRedis;

    @Override
    public R<?> createUserToken(long userId, boolean fromPhone) {//login
        SysUserEntity user = sysUserService.getById(userId);
        List<SysUserTokenEntity> tokenEntitys = this.list(Wrappers.<SysUserTokenEntity>query()
                .lambda()
                .eq(SysUserTokenEntity::getUserId, userId));

        SysUserTokenEntity tokenEntity = null;
        if (!user.getMultilogin()) {
            if (tokenEntitys != null && tokenEntitys.size() > 0) {
                if (tokenEntitys.size() > 1) {
                    List<SysUserTokenEntity> willDel = this.list(Wrappers.<SysUserTokenEntity>query()
                            .lambda()
                            .eq(SysUserTokenEntity::getUserId, userId));
                    this.remove(Wrappers.<SysUserTokenEntity>query()
                            .lambda()
                            .eq(SysUserTokenEntity::getUserId, userId));
                    for (SysUserTokenEntity se : willDel) {
                        sysUserTokenRedis.delete(se.getId() + "");
                    }
                }
                if (tokenEntitys.size() == 1) {
                    tokenEntity = tokenEntitys.get(0);
                }
            }
        }
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            this.save(tokenEntity);
        }

        //生成一个token
//		String tokenAth2 = TokenGenerator.generateValue();
		//当前时间
		Date now = new Date();
        long expire = fromPhone ? jwtUtils.getExpirePhone() : jwtUtils.getExpireWeb();
		//过期时间
		Date expireTime = new Date(now.getTime() + expire * 1000);

        String token = jwtUtils.generateUserToken(userId, tokenEntity.getId(), fromPhone ? "phone" : "web");
        //判断是否生成过token
        tokenEntity.setToken(token);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(expireTime);
        this.updateById(tokenEntity);
        sysUserTokenRedis.saveOrUpdate(tokenEntity);

        R<?> r = R.mapOk().put("token", token).put("expire", expire);
        return r;
    }

    @Override
    public R<?> createDeviceToken(String imei, boolean fromPhone) {
        String token = jwtUtils.generateDeviceToken(imei, 0, fromPhone ? "phone" : "web");
        R<?> r = R.mapOk()
                .put("token", token)
                .put("expire", fromPhone ? jwtUtils.getExpirePhone() : jwtUtils.getExpireWeb());
        return r;
    }

    @Override
    public SysUserTokenEntity getById2(long id) {
        SysUserTokenEntity token = sysUserTokenRedis.get(id + "");
        if (token == null) {
            token = baseMapper.selectById(id);
            sysUserTokenRedis.saveOrUpdate(token);
        }
        return token;
    }

    @Override
    public void logout() {
        SysUserEntity u = ShiroUtils.getUserEntity();
        DeviceEntity d = ShiroUtils.getDeviceEntity();
        if (u != null) {
            long tokenId = ShiroUtils.getGpsPrincipal().getTokenId();
            removeById(tokenId);
            sysUserTokenRedis.delete(tokenId+"");
        }
        //生成一个token
//		String token = TokenGenerator.generateValue();

        //修改token
//        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
//        tokenEntity.setUserId(userId);
//        tokenEntity.setToken("error");
//        this.updateById(tokenEntity);
    }

    @Override
    public void logoutById(Long userId) {
        List<SysUserTokenEntity> l = list(Wrappers.<SysUserTokenEntity>lambdaQuery()
                .eq(SysUserTokenEntity::getUserId, userId));
        for (SysUserTokenEntity e : l) {
            String tok = e.getToken();
            long tokenId = e.getId();
            removeById(tokenId);
            sysUserTokenRedis.delete(tokenId+"");
        }
    }

    @Override
    public void checkExpiredToken() {
        remove(Wrappers.<SysUserTokenEntity>query().lambda().lt(SysUserTokenEntity::getExpireTime, new Date()));
    }
}
