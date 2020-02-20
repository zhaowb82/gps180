package com.gps.db.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gps.db.datascope.PermissionAop;
import com.gps.db.entity.AlarmEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
@Mapper
public interface AlarmDao extends BaseMapper<AlarmEntity> {

    @PermissionAop(tableAlias = "a")
    IPage<AlarmEntity> listPage(@Param("page") IPage<AlarmEntity> page, @Param("imei") String imei);
}
