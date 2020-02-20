package com.gps.db.dao;

import com.gps.db.entity.Jt809UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-26 09:26:45
 */
@Mapper
public interface Jt809UserDao extends BaseMapper<Jt809UserEntity> {

    List<Jt809UserEntity> findAllUpUsers();
}
