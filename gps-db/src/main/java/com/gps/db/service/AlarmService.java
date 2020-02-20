package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.AlarmEntity;

import java.util.Map;

/**
 * 
 *
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
public interface AlarmService extends IService<AlarmEntity> {

    MyPage<AlarmEntity> queryPage(Map<String, Object> params);

    MyPage<AlarmEntity> queryPage(Long pageNum, Long pageSize, String orderField, String order, String imei);

    AlarmEntity findLastUnprocessAlarm(String imei, String alarm);

    boolean dismissAlarm(String imei, String alarmId, String prosUser, String remark);
}

