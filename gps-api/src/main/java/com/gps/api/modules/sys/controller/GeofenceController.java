package com.gps.api.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Joiner;
import com.gps.api.common.annotation.SysLog;
import com.gps.db.entityvo.DeviceGeofenceBindParam;
import com.gps.db.entityvo.GeofenceListVo;
import com.gps.db.service.DeviceGeofenceService;
import com.gps.db.entity.DeviceGeofenceEntity;
import com.gps.db.entity.GeofenceEntity;
import com.gps.db.service.GeofenceService;
import com.gps.db.utils.R;
import com.gps.db.utils.SnowFlakeFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qulong
 * @date 2020/1/17 10:36
 * @description
 */
@RestController
@RequestMapping("geofence")
@Api(tags = "GPS围栏管理")
public class GeofenceController extends AbstractController {

    @Autowired
    private GeofenceService geofenceService;

    @Autowired
    private DeviceGeofenceService deviceGeofenceService;

    @GetMapping(value = "")
    @ApiOperation(value = "查询围栏列表")
    @RequiresPermissions("geofence:list")
    public R geofences() {
        String companyId = getUser().getDeptId() + "";
//        List<GeofenceEntity> list = geofenceService.list(Wrappers.<GeofenceEntity>query().lambda().eq(GeofenceEntity::getCompanyId, companyId));
        List<GeofenceListVo> list = geofenceService.getGeofenceList(companyId);
        return R.ok(list);
    }

    @SysLog("添加围栏")
    @PostMapping(value = "")
    @ApiOperation(value = "添加围栏")
    @RequiresPermissions("geofence:save")
    public R saveGeofence(@RequestBody GeofenceEntity geofenceEntity) {
        String companyId = getUser().getDeptId() + "";
        geofenceEntity.setId("geofence-" + SnowFlakeFactory.getSnowFlake().nextIdStr());
        geofenceEntity.setCreateTime(new Date());
        geofenceEntity.setCompanyId(companyId);
        geofenceService.save(geofenceEntity);
        return R.ok();
    }

    @SysLog("修改围栏")
    @PutMapping(value = "")
    @ApiOperation(value = "修改围栏")
    @RequiresPermissions("geofence:update")
    public R updateGeofence(@RequestBody GeofenceEntity geofenceEntity) {
        geofenceEntity.setUpdateTime(new Date());
        geofenceService.updateById(geofenceEntity);
        return R.ok();
    }

    @GetMapping(value = "{geoId}")
    @ApiOperation(value = "取围栏里的设备号")
    @Deprecated
    public R<?> getImeiBygeoId(@PathVariable String geoId) {
        List<String> imeis = deviceGeofenceService.list(
                        Wrappers.<DeviceGeofenceEntity>lambdaQuery().eq(DeviceGeofenceEntity::getGeofenceId, geoId)
                                .select(DeviceGeofenceEntity::getImei)
                )
                .stream()
                .filter(Objects::nonNull)
                .map(DeviceGeofenceEntity::getImei)
                .collect(Collectors.toList());
        return R.ok(imeis);
    }

    @SysLog("删除围栏")
    @DeleteMapping(value = "{geoId}")
    @ApiOperation(value = "删除围栏")
    @RequiresPermissions("geofence:delete")
    public R delGeofences(@PathVariable String geoId) {
        deviceGeofenceService.remove(Wrappers.<DeviceGeofenceEntity>lambdaQuery().eq(DeviceGeofenceEntity::getGeofenceId, geoId));
        geofenceService.removeById(geoId);
        return R.ok("Success");
    }

    @SysLog("围栏绑定")
    @PostMapping(value = "bind")
    @ApiOperation(value = "围栏绑定")
    @RequiresPermissions("geofence:bind")
    public R bind(@RequestBody DeviceGeofenceBindParam deviceGeofenceBindParam) {
        List<DeviceGeofenceEntity> deviceGeofenceEntitys = deviceGeofenceService.list(
                Wrappers.<DeviceGeofenceEntity>lambdaQuery().in(DeviceGeofenceEntity::getImei, deviceGeofenceBindParam.getImeis()));

        if (!Objects.isNull(deviceGeofenceEntitys) && deviceGeofenceEntitys.size() > 0) {
            final List<String> bindedImei = deviceGeofenceEntitys.stream().map(DeviceGeofenceEntity::getImei).collect(Collectors.toList());
            final String imeis = Joiner.on(',').join(bindedImei);
            return R.error(200, String.format("设{}备已绑定围栏", imeis));
        }

        deviceGeofenceEntitys = new ArrayList<>(deviceGeofenceBindParam.getImeis().size());

        for(String imei : deviceGeofenceBindParam.getImeis()) {
            DeviceGeofenceEntity deviceGeofenceEntity = new DeviceGeofenceEntity();
            deviceGeofenceEntity.setId("bind-" + SnowFlakeFactory.getSnowFlake().nextIdStr());
            deviceGeofenceEntity.setImei(imei);
            deviceGeofenceEntity.setGeofenceId(deviceGeofenceBindParam.getGeofenceId());

            deviceGeofenceEntitys.add(deviceGeofenceEntity);
        }

        deviceGeofenceService.saveBatch(deviceGeofenceEntitys);
        return R.ok();
    }

    @SysLog("围栏解绑")
    @DeleteMapping(value = "unbind")
    @ApiOperation(value = "围栏解绑")
    @RequiresPermissions("geofence:unbind")
    public R unbind(@RequestBody DeviceGeofenceBindParam deviceGeofenceParam) {
        List<String> ids = deviceGeofenceService.list(
                Wrappers.<DeviceGeofenceEntity>lambdaQuery().in(DeviceGeofenceEntity::getImei, deviceGeofenceParam.getImeis()))
                .stream()
                .map(DeviceGeofenceEntity::getId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids)) {
            return R.error(200, "参数有误");
        }
        deviceGeofenceService.removeByIds(ids);
        return R.ok();
    }
}
