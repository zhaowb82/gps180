package com.gps.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gps.db.datascope.PermissionAop;
import com.gps.db.entity.DeviceStatusEntity;
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
public interface DeviceStatusDao extends BaseMapper<DeviceStatusEntity> {

    @PermissionAop(tableAlias = "g")
    List<DeviceStatusEntity> selectByImei(@Param("ids") List<String> ids);

//    @Override
//    @PermissionAop
//    DeviceStatusEntity selectById(Serializable id);

//    @Override
//    @PermissionAop
//    List<DeviceStatusEntity> selectList(Wrapper<DeviceStatusEntity> queryWrapper);
}
