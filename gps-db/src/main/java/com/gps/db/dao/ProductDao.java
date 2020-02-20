package com.gps.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gps.db.entity.ProductEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-24 17:23:20
 */
@Mapper
public interface ProductDao extends BaseMapper<ProductEntity> {
	
}
