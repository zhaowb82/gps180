package com.gps.db.dao;

import com.gps.db.entity.UserGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-30 11:13:45
 */
@Mapper
public interface UserGroupDao extends BaseMapper<UserGroupEntity> {

    List<String> queryGroupIdList(long userId);
}
