package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.DeviceGeofenceEntity;

import java.util.Map;

/**
 * 
 *
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
public interface DeviceGeofenceService extends IService<DeviceGeofenceEntity> {

    MyPage<DeviceGeofenceEntity> queryPage(Map<String, Object> params);
}

