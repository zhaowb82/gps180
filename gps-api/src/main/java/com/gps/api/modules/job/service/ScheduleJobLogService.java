/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.api.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	MyPage<ScheduleJobLogEntity> queryPage(Map<String, Object> params);
	
}
