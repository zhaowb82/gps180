package com.gps.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gps.db.entity.Jt809DevPlatEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-10-26 09:26:45
 */
@Mapper
public interface Jt809DevPlatDao extends BaseMapper<Jt809DevPlatEntity> {

    List<String> getGnssByImei(String imei);
}
