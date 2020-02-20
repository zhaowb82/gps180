/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 * <p>
 * https://www.gps180.com
 * <p>
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.service.impl;

import com.gps.api.ApiRedisUtils;
import com.gps.db.dbutils.Constant;
import com.gps.api.modules.sys.dao.SysMenuDao;
import com.gps.api.modules.sys.dao.SysUserDao;
import com.gps.api.modules.sys.dao.SysUserTokenDao;
import com.gps.api.modules.sys.entity.SysMenuEntity;
import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.api.modules.sys.entity.SysUserTokenEntity;
import com.gps.api.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;
    @Autowired
    private ApiRedisUtils apiRedisUtils;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        String key = "redis-user-" + userId;
        SysUserEntity u = apiRedisUtils.get(key, SysUserEntity.class);
        if (u == null) {
            u = sysUserDao.selectById(userId);
            apiRedisUtils.set(key, u, 60*10);
        }
        return u;
    }
}
