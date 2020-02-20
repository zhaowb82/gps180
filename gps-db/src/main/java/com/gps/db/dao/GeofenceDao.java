package com.gps.db.dao;

import com.gps.db.entity.GeofenceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gps.db.entityvo.GeofenceListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
@Mapper
public interface GeofenceDao extends BaseMapper<GeofenceEntity> {

    List<GeofenceEntity> getGeofenceByDeviceImei(@Param("deviceImei") String deviceImei);

    List<GeofenceListVo> geofenceList(@Param("companyId") String companyId);
}
