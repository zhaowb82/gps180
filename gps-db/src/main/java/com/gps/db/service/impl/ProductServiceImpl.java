package com.gps.db.service.impl;

import com.gps.db.dao.ProductDao;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.ProductEntity;
import com.gps.db.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {

    @Override
    public MyPage<ProductEntity> queryPage(Map<String, Object> params) {
        IPage<ProductEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<ProductEntity>()
        );

        return MyPage.create(page);
    }

}