package com.gps.db.service.impl;

import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.db.service.UserDeviceService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gps.db.dao.UserDeviceDao;
import com.gps.db.entity.UserDeviceEntity;


@Service("userDeviceService")
public class UserDeviceServiceImpl extends ServiceImpl<UserDeviceDao, UserDeviceEntity> implements UserDeviceService {

    @Override
    public MyPage<UserDeviceEntity> queryPage(Map<String, Object> params) {
        IPage<UserDeviceEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<UserDeviceEntity>()
        );

        return MyPage.create(page);
    }

}