package com.gps.db.service.impl;

import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.db.service.PositionsService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gps.db.dao.PositionsDao;
import com.gps.db.entity.PositionsEntity;


@Service("positionsService")
public class PositionsServiceImpl extends ServiceImpl<PositionsDao, PositionsEntity> implements PositionsService {

    @Override
    public MyPage<PositionsEntity> queryPage(Map<String, Object> params) {
        IPage<PositionsEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<PositionsEntity>()
        );

        return MyPage.create(page);
    }

}