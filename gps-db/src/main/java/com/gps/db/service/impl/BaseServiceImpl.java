package com.gps.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gps.db.dbutils.MyPage;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.service.BaseIService;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseIService<T> {

    public MyPage<T> queryPage(Long pageNum, Long pageSize, String sort, String order,
                               Wrapper<T> queryWrapper) {
        IPage<T> pageO = MyQuery.getPage(pageNum, pageSize, sort, order);
        IPage<T> page = this.page(pageO, queryWrapper);
        return MyPage.create(page);
    }

}