package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.entity.CommandLogsEntity;
import com.gps.db.dbutils.MyPage;

import java.util.Date;
import java.util.Map;

/**
 * 
 *
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
public interface CommandLogsService extends IService<CommandLogsEntity> {

    MyPage<CommandLogsEntity> queryPage(Map<String, Object> params);

    MyPage<CommandLogsEntity> queryPage(Long pageNum, Long pageSize, String orderField, String order,
                                        String imei, Date startDate, Date endDate);
}

