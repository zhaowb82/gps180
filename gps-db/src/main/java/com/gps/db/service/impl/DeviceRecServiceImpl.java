package com.gps.db.service.impl;

import com.gps.db.dao.DeviceRecDao;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.CommandLogsEntity;
import com.gps.db.entity.DeviceRecEntity;
import com.gps.db.service.DeviceRecService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service("deviceRecService")
public class DeviceRecServiceImpl extends ServiceImpl<DeviceRecDao, DeviceRecEntity> implements DeviceRecService {

    @Override
    public MyPage<DeviceRecEntity> queryPage(Map<String, Object> params) {
        IPage<DeviceRecEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<DeviceRecEntity>()
        );

        return MyPage.create(page);
    }

    @Override
    public MyPage<DeviceRecEntity> listAll(Long pageNum, Long pageSize, String sort, String order,
                                         String imei, Date startDate, Date endDate) {
        IPage<DeviceRecEntity> pageO = MyQuery.getPage(pageNum, pageSize, sort, order);
        IPage<DeviceRecEntity> page = baseMapper.listAll(pageO, imei, startDate, endDate);
        return MyPage.create(page);
    }

}