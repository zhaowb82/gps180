package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.common.model.CommandType;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.CommandTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-30 11:13:45
 */
public interface CommandTypeService extends IService<CommandTypeEntity> {

    MyPage<CommandTypeEntity> queryPage(Map<String, Object> params);

    List<CommandTypeEntity> getByImei(String imei);
}

