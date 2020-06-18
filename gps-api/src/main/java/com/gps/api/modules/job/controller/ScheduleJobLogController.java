/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.job.controller;

import com.gps.db.dbutils.MyPage;
import com.gps.db.utils.R;
import com.gps.api.modules.job.entity.ScheduleJobLogEntity;
import com.gps.api.modules.job.service.ScheduleJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 定时任务日志
 */
@RestController
@RequestMapping("/sys/scheduleLog")
@Api(tags = "S定时日志")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	@ApiOperation(value = "列表")
	public R<MyPage<ScheduleJobLogEntity>> list(@RequestParam Map<String, Object> params){
		MyPage<ScheduleJobLogEntity> page = scheduleJobLogService.queryPage(params);
		
		return R.ok(page);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@GetMapping("/info/{logId}")
	@ApiOperation(value = "单条")
	public R info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);
		
		return R.ok(log);
	}
}
