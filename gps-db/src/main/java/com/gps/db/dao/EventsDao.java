package com.gps.db.dao;

import com.gps.db.entity.EventsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
@Mapper
public interface EventsDao extends BaseMapper<EventsEntity> {
	
}
