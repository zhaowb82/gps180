package com.gps.db.service.impl;

import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gps.db.dao.AlarmTypeDao;
import com.gps.db.entity.AlarmTypeEntity;
import com.gps.db.service.AlarmTypeService;


@Service("alarmTypeService")
public class AlarmTypeServiceImpl extends ServiceImpl<AlarmTypeDao, AlarmTypeEntity> implements AlarmTypeService {

    @Override
    public MyPage<AlarmTypeEntity> queryPage(Map<String, Object> params) {
        IPage<AlarmTypeEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<AlarmTypeEntity>()
        );

        return MyPage.create(page);
    }

}