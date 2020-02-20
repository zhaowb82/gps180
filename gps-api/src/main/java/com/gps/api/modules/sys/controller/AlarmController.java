package com.gps.api.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gps.api.common.annotation.SysLog;
import com.gps.db.dbutils.MyPage;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.entity.AlarmEntity;
import com.gps.db.entity.DeviceEntity;
import com.gps.db.entity.EventsEntity;
import com.gps.db.service.AlarmService;
import com.gps.db.service.EventsService;
import com.gps.db.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gps.db.service.AlarmTypeService;


/**
 * 
 *
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-30 11:13:45
 */
@RestController
@RequestMapping("alarm")
@Api(tags = "GPS报警管理")
public class AlarmController extends AbstractController {
    @Autowired
    private AlarmTypeService alarmTypeService;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private EventsService eventsService;

//    /**
//     * 列表
//     */
//    @RequestMapping("/list")
//    @RequiresPermissions("common:alarmtype:list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = alarmTypeService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }
//
//
//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{alarmcode}")
//    @RequiresPermissions("common:alarmtype:info")
//    public R info(@PathVariable("alarmcode") String alarmcode){
//		AlarmTypeEntity alarmType = alarmTypeService.getById(alarmcode);
//
//        return R.ok().put("alarmType", alarmType);
//    }
//
//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    @RequiresPermissions("common:alarmtype:save")
//    public R save(@RequestBody AlarmTypeEntity alarmType){
//		alarmTypeService.save(alarmType);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    @RequiresPermissions("common:alarmtype:update")
//    public R update(@RequestBody AlarmTypeEntity alarmType){
//		alarmTypeService.updateById(alarmType);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("common:alarmtype:delete")
//    public R delete(@RequestBody String[] alarmcodes){
//		alarmTypeService.removeByIds(Arrays.asList(alarmcodes));
//
//        return R.ok();
//    }

    @GetMapping(value = "alarmDescr")
    @ApiOperation(value = "")
//    @RequiresPermissions("alarm:list")
    public R<?> alarmDescr() {
        return R.ok(alarmTypeService.list());
    }

    @GetMapping(value = "alarms")
    @ApiOperation(value = "查询报警信息分页-右下角")
//    @RequiresPermissions("alarm:listHome")
    public R<?> alarms(@RequestParam(required = false) Long pageNum,
                        @RequestParam(required = false) Long pageSize,
                        @RequestParam(required = false, defaultValue = "occur_time") String orderField,
                        @RequestParam(required = false, defaultValue = "DESC") String order) {
        IPage<AlarmEntity> page = MyQuery.getPage(pageNum, pageSize, orderField, order);
        DeviceEntity d = getDevice();
        if (d != null) {
            LambdaQueryWrapper<AlarmEntity> w = Wrappers.<AlarmEntity>query().lambda().eq(AlarmEntity::getImei, d.getImei());
            IPage<AlarmEntity> list = alarmService.page(page, w);
            return R.ok(MyPage.create(list));
        }
        IPage<AlarmEntity> list = alarmService.page(page, Wrappers.<AlarmEntity>query().lambda().eq(AlarmEntity::getCompanyId, getUser().getDeptId()));
        return R.ok(MyPage.create(list));
    }

    @GetMapping(value = "allAlarms")
    @ApiOperation(value = "查询报警信息分页-统计")
//    @RequiresPermissions("alarm:list")
    public R<?> allAlarms(@RequestParam(required = false) Long pageNum,
                          @RequestParam(required = false) Long pageSize,
                          @RequestParam(required = false) String imei,
                          @RequestParam(required = false, defaultValue = "occur_time") String orderField,
                          @RequestParam(required = false, defaultValue = "DESC") String order) {
        MyPage<AlarmEntity> list = alarmService.queryPage(pageNum, pageSize, orderField, order, imei);
        return R.ok(list);
    }

    @GetMapping(value = "msgs")
    @ApiOperation(value = "查询Event信息分页-右下角")
    public R msgs(@RequestParam(required = false) Long pageNum,
                  @RequestParam(required = false) Long pageSize,
                  @RequestParam(required = false, defaultValue = "occur_time") String orderField,
                  @RequestParam(required = false, defaultValue = "DESC") String order, String lastqueryallmsgtime) {
        IPage<EventsEntity> page = MyQuery.getPage(pageNum, pageSize, orderField, order);
//        eventsService.listByUser();
        DeviceEntity d = getDevice();
        if (d != null) {
            IPage<EventsEntity> list = eventsService.page(page, Wrappers.<EventsEntity>query().lambda().eq(EventsEntity::getImei, d.getImei()));
            return R.ok(MyPage.create(list));
        }
        IPage<EventsEntity> list = eventsService.page(page, Wrappers.<EventsEntity>query().lambda().eq(EventsEntity::getCompanyId, getUser().getDeptId()));
        return R.ok(MyPage.create(list));
    }

    @SysLog("删除事件")
    @DeleteMapping(value = "delEvent")
    @ApiOperation(value = "删除事件")
    @RequiresPermissions("alarm:delete")
    public R delEvent(String evtId) {
        eventsService.removeById(evtId);
        return R.ok();
    }

    @SysLog("删除报警")
    @DeleteMapping(value = "delAlarm")
    @ApiOperation(value = "删除报警")
    @RequiresPermissions("alarm:delete")
    public R delAlarm(String alarmId) {
        alarmService.removeById(alarmId);
        return R.ok();
    }
}
