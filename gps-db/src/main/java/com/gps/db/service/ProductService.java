package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.ProductEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-24 17:23:20
 */
public interface ProductService extends IService<ProductEntity> {

    MyPage<ProductEntity> queryPage(Map<String, Object> params);
}

