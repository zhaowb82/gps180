package com.gps.db.service.impl;

import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.DeviceStatusEntity;
import com.gps.db.entityvo.GeofenceListVo;
import com.gps.db.service.GeofenceService;
import com.gps.db.utils.GeofenceUtil;
import com.gps.common.model.Geofence;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gps.db.dao.GeofenceDao;
import com.gps.db.entity.GeofenceEntity;
import com.gps.common.model.Position;


@Service("geofenceService")
public class GeofenceServiceImpl extends ServiceImpl<GeofenceDao, GeofenceEntity> implements GeofenceService {

    @Override
    public MyPage<GeofenceEntity> queryPage(Map<String, Object> params) {
        IPage<GeofenceEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<GeofenceEntity>()
        );

        return MyPage.create(page);
    }

    @Override
    public List<GeofenceListVo> getGeofenceList(String companyId) {
        return baseMapper.geofenceList(companyId);
    }

    @Override
    public List<String> getCurrentDeviceGeofences(Position position) {
        List<String> result = new ArrayList<>();
        List<GeofenceEntity> gs = baseMapper.getGeofenceByDeviceImei(position.getImei());
        if (gs != null) {
            for (GeofenceEntity geo : gs) {
                Geofence geofence = GeofenceUtil.fromEntityGeofence(geo);
                if (geofence.getGeometry().containsPoint(position.getLatitude(), position.getLongitude())) {
                    geofence.setImei(position.getImei());
                    result.add(geo.getId());
                }
            }
        }
        return result;
    }

    @Override
    public int calculateDevicesGeofences(DeviceStatusEntity deviceStatus, Position position) {
        List<GeofenceEntity> geofences = baseMapper.getGeofenceByDeviceImei(position.getImei());
        if (geofences == null || geofences.size() == 0) {       //未绑定围栏
            return 0;
        }

        double longitude = position.getLongitude() == 0 ? deviceStatus.getLongitude() : position.getLongitude();
        double latitude = position.getLatitude() == 0 ? deviceStatus.getLatitude() : position.getLatitude();

        if (longitude == 0 || latitude == 0) {
            return 0;
        }

        //围栏校验
        for (GeofenceEntity geofence : geofences) {   //已绑定围栏
            Geofence geofence1 = GeofenceUtil.fromEntityGeofence(geofence);
            boolean geofenceIn = geofence1.getGeometry().containsPoint(latitude, longitude);
            if (geofenceIn) {
                return 1;
            }
        }
        return -1;
    }

}