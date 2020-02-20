package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.DeviceRecEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author zb
 * @email zb@gmail.com
 * @date 2020-01-22 16:33:18
 */
public interface DeviceRecService extends IService<DeviceRecEntity> {

    MyPage<DeviceRecEntity> queryPage(Map<String, Object> params);

    MyPage<DeviceRecEntity> listAll(Long pageNum, Long pageSize, String sort, String order,
                                    String imei, Date startDate, Date endDate);
}

