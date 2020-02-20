package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.UserDeviceEntity;

import java.util.Map;

/**
 * 
 *
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-30 11:13:45
 */
public interface UserDeviceService extends IService<UserDeviceEntity> {

    MyPage<UserDeviceEntity> queryPage(Map<String, Object> params);
}

