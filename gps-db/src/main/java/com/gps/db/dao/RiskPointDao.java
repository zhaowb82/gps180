package com.gps.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gps.db.datascope.PermissionAop;
import com.gps.db.entity.AlarmEntity;
import com.gps.db.entity.RiskPointEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author qulpng
 * @email qul@ccwcar.com
 * @date 2020-02-03 14:27:53
 */
@Mapper
public interface RiskPointDao extends BaseMapper<RiskPointEntity> {
}
