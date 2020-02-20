package com.gps.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gps.db.datascope.PermissionAop;
import com.gps.db.entity.DeviceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-24 17:23:20
 */
@Mapper
public interface DeviceDao extends BaseMapper<DeviceEntity> {

    @PermissionAop(tableAlias = "d")
    List<DeviceEntity> listByUser();

    @PermissionAop(tableAlias = "d")
    IPage<DeviceEntity> bindGeofenceDevice(@Param("page") IPage<DeviceEntity> page, @Param("companyId") String companyId, @Param("imei") String imei, @Param("geoId") String geoId);

    @PermissionAop(tableAlias = "d")
    IPage<DeviceEntity> unbindGeofenceDevice(@Param("page") IPage<DeviceEntity> page, @Param("companyId") String companyId, @Param("imei") String imei);

    @PermissionAop(tableAlias = "d")
    List<String> listByImei(String imei);

    @PermissionAop(tableAlias = "d")
    IPage<DeviceEntity> listPage(@Param("page") IPage<DeviceEntity> page,
                                 @Param("groupId") String groupId, @Param("imei") String imei);
}
