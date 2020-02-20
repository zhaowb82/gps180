package com.gps.db.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gps.db.dbutils.MyPage;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.entity.DeviceStatusEntity;
import com.gps.db.service.DeviceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gps.db.dao.AlarmDao;
import com.gps.db.entity.AlarmEntity;
import com.gps.db.service.AlarmService;


@Service("alarmService")
public class AlarmServiceImpl extends ServiceImpl<AlarmDao, AlarmEntity> implements AlarmService {

    @Autowired
    private DeviceStatusService deviceStatusService;

    @Override
    public MyPage<AlarmEntity> queryPage(Map<String, Object> params) {
        IPage<AlarmEntity> page = this.page(MyQuery.getPage(params), new QueryWrapper<>());

        return MyPage.create(page);
    }

    @Override
    public MyPage<AlarmEntity> queryPage(Long pageNum, Long pageSize, String orderField, String order, String imei) {
//        QueryWrapper<AlarmEntity> queryWrapper = new QueryWrapper<AlarmEntity>().eq("company_id", companyId);
//        if (StringUtils.isNotBlank(imei)) {
//            queryWrapper.like("imei", imei);
//        }
////        queryWrapper.orderBy(true, false, "occur_time");
//
//        IPage<AlarmEntity> page = this.page(MyQuery.getPage(pageNum, pageSize, orderField, order), queryWrapper);
        IPage<AlarmEntity> page = baseMapper.listPage(MyQuery.getPage(pageNum, pageSize, orderField, order), imei);
        return MyPage.create(page);
    }

    @Override
    public AlarmEntity findLastUnprocessAlarm(String imei, String alarmType) {
        return baseMapper.selectOne(Wrappers.<AlarmEntity>query()
                .lambda()
                .eq(AlarmEntity::getImei, imei)
                .eq(AlarmEntity::getType, alarmType)
                .eq(AlarmEntity::getProcessStatus, 0)
        );
    }

    @Override
    public boolean dismissAlarm(String imei, String alarmId, String prosUser, String remark) {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setId(alarmId);
        alarmEntity.setProcessStatus(1);
        alarmEntity.setProcessUser(prosUser);
        alarmEntity.setProcessRemark(remark);
        if (baseMapper.updateById(alarmEntity) > 0) {
            //更新status
            DeviceStatusEntity deviceStatusEntity = deviceStatusService.getDeviceWithCache(imei);
            deviceStatusEntity.setAlarmStatus(0);
            deviceStatusService.updateDeviceWithCache(deviceStatusEntity, true, true);
            return true;
        }
        return false;
    }
}