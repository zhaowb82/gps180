package com.gps.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gps.db.datascope.PermissionAop;
import com.gps.db.entity.DeviceRecEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2020-01-22 16:33:18
 */
@Mapper
public interface DeviceRecDao extends BaseMapper<DeviceRecEntity> {

    @PermissionAop(tableAlias = "d")
    IPage<DeviceRecEntity> listAll(@Param("page") IPage<DeviceRecEntity> page, @Param("imei") String imei,
                                   @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
