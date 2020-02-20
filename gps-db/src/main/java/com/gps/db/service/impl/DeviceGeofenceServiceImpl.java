package com.gps.db.service.impl;

import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gps.db.dao.DeviceGeofenceDao;
import com.gps.db.entity.DeviceGeofenceEntity;
import com.gps.db.service.DeviceGeofenceService;


@Service("deviceGeofenceService")
public class DeviceGeofenceServiceImpl extends ServiceImpl<DeviceGeofenceDao, DeviceGeofenceEntity> implements DeviceGeofenceService {

    @Override
    public MyPage<DeviceGeofenceEntity> queryPage(Map<String, Object> params) {
        IPage<DeviceGeofenceEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<DeviceGeofenceEntity>()
        );

        return MyPage.create(page);
    }

}