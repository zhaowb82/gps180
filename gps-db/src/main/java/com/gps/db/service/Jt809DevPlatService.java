package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.Jt809DevPlatEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-26 09:26:45
 */
public interface Jt809DevPlatService extends IService<Jt809DevPlatEntity> {

    MyPage<Jt809DevPlatEntity> queryPage(Map<String, Object> params);
    List<String> getGnssByImei(String imei);

}

