/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 * <p>
 * https://www.gps180.com
 * <p>
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gps.api.modules.sys.entity.SysLogEntity;
import com.gps.api.modules.sys.service.SysLogService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.utils.R;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:log:list")
    public R<MyPage<SysLogEntity>> list(@RequestParam(required = false) Long pageNum,
										@RequestParam(required = false) Long pageSize,
										@RequestParam(required = false) String key,
										@RequestParam(required = false) Date startDate,
										@RequestParam(required = false) Date endDate,
										@RequestParam(required = false, defaultValue = "create_date") String sort,
										@RequestParam(required = false, defaultValue = "DESC") String order) {

		QueryWrapper<SysLogEntity> wrapper = new QueryWrapper<SysLogEntity>()
				.like(StringUtils.isNotBlank(key),"username", key);
        MyPage<SysLogEntity> page = sysLogService.queryPage(pageNum, pageSize, sort, order, wrapper);

        return R.ok(page);
    }

    /**
     * 删除
     */
    @DeleteMapping("delete/{id}")
    @RequiresPermissions("sys:log:delete")
    @ApiOperation(value = "删除")
    public R delete(@PathVariable String id) {
        sysLogService.removeById(id);
        return R.ok();
    }

}
