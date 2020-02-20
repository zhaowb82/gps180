package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.DeviceStatusEntity;
import com.gps.db.entity.GeofenceEntity;
import com.gps.common.model.Position;

import com.gps.db.entityvo.GeofenceListVo;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
public interface GeofenceService extends IService<GeofenceEntity> {

    MyPage<GeofenceEntity> queryPage(Map<String, Object> params);

    List<String> getCurrentDeviceGeofences(Position position);

    List<GeofenceListVo> getGeofenceList(String companyId);

    int calculateDevicesGeofences(DeviceStatusEntity deviceStatus, Position position);
}

