package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.DeviceStatusEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-24 17:23:20
 */
public interface DeviceStatusService extends IService<DeviceStatusEntity> {

    void updateDeviceWithCache(DeviceStatusEntity deviceStatus, boolean upDb, boolean upCache);

    DeviceStatusEntity getDeviceWithCache(String imei);

    List<DeviceStatusEntity> selectByImei(List<String> list);

    void checkOfflineDevice();

    void removeByImei(String imei);
}

