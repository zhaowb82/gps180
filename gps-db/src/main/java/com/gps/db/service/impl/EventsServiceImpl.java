package com.gps.db.service.impl;

import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gps.db.dao.EventsDao;
import com.gps.db.entity.EventsEntity;
import com.gps.db.service.EventsService;


@Service("eventsService")
public class EventsServiceImpl extends ServiceImpl<EventsDao, EventsEntity> implements EventsService {

    @Override
    public MyPage<EventsEntity> queryPage(Map<String, Object> params) {
        IPage<EventsEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<EventsEntity>()
        );

        return MyPage.create(page);
    }

}