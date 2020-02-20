package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.DeviceEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-24 17:23:20
 */
public interface DeviceService extends IService<DeviceEntity> {

    MyPage<DeviceEntity> queryPage(Map<String, Object> params);

    MyPage<DeviceEntity> listPage(Long pageNum, Long pageSize, String orderField, String order, String groupId, String imei);

    MyPage<DeviceEntity> bindGeofenceList(Long pageNum, Long pageSize, String orderField, String order, String companyId, String imei, String geoId);

    MyPage<DeviceEntity> unbindGeofenceList(Long pageNum, Long pageSize, String orderField, String order, String companyId, String imei);

    List<DeviceEntity> listByUser();

    List<String> listByImei(String imei);

    boolean updatePassword(String imei, String password, String newPassword);
}

