package com.gps.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gps.db.dao.DeviceStatusDao;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.DeviceStatusEntity;
import com.gps.db.GpsRedisUtils;
import com.gps.db.service.DeviceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gps.common.cache.CacheKeys;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("deviceStatusService")
public class DeviceStatusServiceImpl extends ServiceImpl<DeviceStatusDao, DeviceStatusEntity> implements DeviceStatusService {

    @Autowired
    private GpsRedisUtils redisUtils;

    @Override
    public void updateDeviceWithCache(DeviceStatusEntity deviceStatus, boolean upDb, boolean upCache) {
        if (deviceStatus == null) {
            return;
        }
        if (upDb) {
//            updateSelectiveById(device);
//            deviceStatus.setAttribute(null);
            updateById(deviceStatus);
        }
        if (upCache) {
            redisUtils.set(CacheKeys.getDeviceByUniqueId(deviceStatus.getImei()), deviceStatus, 60*60*24);
        }
    }

    @Override
    public DeviceStatusEntity getDeviceWithCache(String imei) {
        DeviceStatusEntity device = null;
        String key = CacheKeys.getDeviceByUniqueId(imei);
        try {
            device = redisUtils.get(key, DeviceStatusEntity.class);
        } catch (Exception e) {
            redisUtils.delete(key);
        }
        if (device == null) {
            DeviceStatusEntity dev = getById(imei);
            if (dev != null) {
                device = dev;
                redisUtils.set(key, device, 60*60*24);
            }
        }
        return device;
    }

    @Override
    public List<DeviceStatusEntity> selectByImei(List<String> list) {
        return baseMapper.selectByImei(list);
    }

    @Override
    public void checkOfflineDevice() {
        Date now = new Date();
        Date expireTime = new Date(now.getTime() - 10 * 60 * 1000);
        update(Wrappers.<DeviceStatusEntity>update().lambda()
                .set(DeviceStatusEntity::getConnectionStatus, "offline")
                .lt(DeviceStatusEntity::getSignalUpdateTime, expireTime)
//                .eq()
        );
    }

    @Override
    public void removeByImei(String imei) {
        String key = CacheKeys.getDeviceByUniqueId(imei);
        redisUtils.delete(key);
        baseMapper.deleteById(imei);
    }

}