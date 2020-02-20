package com.gps.db.dao;

import com.gps.db.entity.CommandTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-30 11:13:45
 */
@Mapper
public interface CommandTypeDao extends BaseMapper<CommandTypeEntity> {

    List<CommandTypeEntity> cmdListByImei(@Param("imei") String imei);
}
