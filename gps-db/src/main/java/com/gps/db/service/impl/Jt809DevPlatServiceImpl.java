package com.gps.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gps.db.dao.Jt809DevPlatDao;
import com.gps.db.dbutils.MyPage;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.entity.Jt809DevPlatEntity;
import com.gps.db.service.Jt809DevPlatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("jt809DevPlatService")
public class Jt809DevPlatServiceImpl extends ServiceImpl<Jt809DevPlatDao, Jt809DevPlatEntity> implements Jt809DevPlatService {

    @Override
    public MyPage<Jt809DevPlatEntity> queryPage(Map<String, Object> params) {
        IPage<Jt809DevPlatEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<>()
        );

        return MyPage.create(page);
    }

    @Override
    public List<String> getGnssByImei(String imei) {
        return baseMapper.getGnssByImei(imei);
    }

}