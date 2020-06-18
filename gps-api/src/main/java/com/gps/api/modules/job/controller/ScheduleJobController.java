
package com.gps.api.modules.job.controller;

import com.gps.db.dbutils.MyPage;
import com.gps.api.common.annotation.SysLog;
import com.gps.db.utils.R;
import com.gps.api.common.validator.ValidatorUtils;
import com.gps.api.modules.job.entity.ScheduleJobEntity;
import com.gps.api.modules.job.service.ScheduleJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 定时任务
 */
@RestController
@RequestMapping("/sys/schedule")
@Api(tags = "S定时操作")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 定时任务列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    @ApiOperation(value = "列表")
    public R list(@RequestParam Map<String, Object> params) {
        MyPage<ScheduleJobEntity> page = scheduleJobService.queryPage(params);

        return R.ok(page);
    }

    /**
     * 定时任务信息
     */
    @GetMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    @ApiOperation(value = "单条")
    public R info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = scheduleJobService.getById(jobId);

        return R.ok(schedule);
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @PostMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    @ApiOperation(value = "保存定时任务")
    public R save(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.saveJob(scheduleJob);

        return R.ok();
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @PutMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    @ApiOperation(value = "修改定时任务")
    public R update(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.update(scheduleJob);

        return R.ok();
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @PostMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    @ApiOperation(value = "删除定时任务")
    public R delete(@RequestBody Long[] jobIds) {
        scheduleJobService.deleteBatch(jobIds);

        return R.ok();
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @RequestMapping(value = "/run", method = RequestMethod.POST)
    @RequiresPermissions("sys:schedule:run")
    @ApiOperation(value = "立即执行任务")
    public R run(@RequestBody Long[] jobIds) {
        scheduleJobService.run(jobIds);

        return R.ok();
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @RequestMapping(value = "/pause", method = RequestMethod.POST)
    @RequiresPermissions("sys:schedule:pause")
    @ApiOperation(value = "暂停定时任务")
    public R pause(@RequestBody Long[] jobIds) {
        scheduleJobService.pause(jobIds);

        return R.ok();
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    @RequiresPermissions("sys:schedule:resume")
    @ApiOperation(value = "恢复定时任务")
    public R resume(@RequestBody Long[] jobIds) {
        scheduleJobService.resume(jobIds);

        return R.ok();
    }

}
