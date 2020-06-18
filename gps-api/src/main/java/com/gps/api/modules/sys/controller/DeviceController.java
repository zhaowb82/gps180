package com.gps.api.modules.sys.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gps.common.helper.HumpUtil;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.DeviceEntity;
import com.gps.db.entity.GroupsEntity;
import com.gps.db.entity.PositionsEntity;
import com.gps.db.entity.ProductEntity;
import com.gps.db.service.DeviceService;
import com.gps.db.service.DeviceStatusService;
import com.gps.db.service.GroupsService;
import com.gps.db.service.PositionsService;
import com.gps.db.service.ProductService;
import com.gps.db.service.UserDeviceService;
import com.gps.db.utils.R;
import com.gps.db.utils.UIDGenerator;
import com.gps.api.common.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-30 11:13:45
 */
@RestController
@RequestMapping("device")
@Api(tags = "GPS设备管理")
public class DeviceController extends AbstractController {

    @Autowired
    private UserDeviceService userDeviceService;
    @Autowired
    private ProductService productService;
    @Autowired
    private GroupsService groupsService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceStatusService deviceStatusService;
    @Autowired
    private PositionsService positionsService;

    /**
     * 列表
     */
    @GetMapping("list")
    @ApiOperation(value = "全部设备-主页用")
    public R<?> list() {
        List<DeviceEntity> list = deviceService.listByUser();
        return R.ok(list);
    }

    @GetMapping("listPage")
    @ApiOperation(value = "全部设备分页-设备管理")
    public R<?> listPage(@RequestParam(required = false) Long pageNum,
                         @RequestParam(required = false) Long pageSize,
                         @RequestParam(required = false, defaultValue = "imei") String sort,
                         @RequestParam(required = false, defaultValue = "ASC") String order,
                         @RequestParam(required = false) String groupId,
                         @RequestParam(required = false) String imei) {
        MyPage<DeviceEntity> list = deviceService.listPage(pageNum, pageSize, HumpUtil.humpToLine(sort), order, groupId, imei);
        return R.ok(list);
    }

    @GetMapping("bindGeofenceList")
    @ApiOperation(value = "绑定围栏的设备列表")
    public R<?> bindGeofenceList(@RequestParam Long pageNum,
                                 @RequestParam Long pageSize,
                                 @RequestParam(required = false) String imei,
                                 @RequestParam(required = false) String geoId,
                                 @RequestParam(required = false, defaultValue = "imei") String orderField,
                                 @RequestParam(required = false, defaultValue = "ASC") String order) {
        MyPage<DeviceEntity> list = deviceService.bindGeofenceList(pageNum, pageSize, orderField, order, getUser().getDeptId() + "", imei, geoId);
        return R.ok(list);
    }

    @GetMapping("unbindGeofenceList")
    @ApiOperation(value = "未绑定围栏设备列表")
    public R<?> unbindGeofenceList(@RequestParam Long pageNum,
                                   @RequestParam Long pageSize,
                                   @RequestParam(required = false) String imei,
                                   @RequestParam(required = false, defaultValue = "imei") String orderField,
                                   @RequestParam(required = false, defaultValue = "ASC") String order) {
        MyPage<DeviceEntity> list = deviceService.unbindGeofenceList(pageNum, pageSize, orderField, order, getUser().getDeptId() + "", imei);
        return R.ok(list);
    }

    @GetMapping("listByImei")
    @ApiOperation(value = "搜索设备前20个-查询时用")
    public R<?> list(@RequestParam String imei) {
        List<String> list = deviceService.listByImei(imei);
        return R.ok(list);
    }

//    @GetMapping(value = "monitorList")
//    @ApiOperation(value = "")
//    @Deprecated
//    public R<?> monitorList() {
//        DeviceEntity d = getDevice();
//        if (d != null) {
//            List<GroupsEntity> list = groupsService.list(Wrappers.<GroupsEntity>query().lambda().eq(GroupsEntity::getId, d.getGroupId()));
//            for (GroupsEntity e : list) {
//                e.setDevices(deviceService.list(Wrappers.<DeviceEntity>query().lambda().eq(DeviceEntity::getImei, d.getImei())));
//            }
//            return R.ok(list);
//        }
//        List<GroupsEntity> list = groupsService.listByUser();
//        for (GroupsEntity e : list) {
//            e.setDevices(deviceService.list(Wrappers.<DeviceEntity>query().lambda().eq(DeviceEntity::getGroupId, e.getId())));
//        }
//        return R.ok(list);
//    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//    @RequiresPermissions("device:info")
    @ApiOperation(value = "信息")
    public R<DeviceEntity> info(@PathVariable("id") String id) {
        DeviceEntity userDevice = deviceService.getById(id);
        return R.ok(userDevice);
    }

    /**
     * 保存
     */
    @SysLog("添加设备")
    @PostMapping("save")
    @RequiresPermissions("device:save")
    @ApiOperation(value = "保存")
    public R<?> save(@RequestBody DeviceEntity deviceEntity) {
        if (deviceEntity == null || deviceEntity.getImei() == null) {
            return R.error(2, "imei error");
        }
        if (deviceEntity.getImei().length() < 8) {
            return R.error(200, "imei 必须大于8位");
        }
        if (deviceService.getById(deviceEntity.getImei()) != null) {
            return R.error(1, "repeate");
        }
        if (deviceEntity.getPassword() != null) {
            String newPassword = DigestUtils.md5Hex(deviceEntity.getPassword());
            deviceEntity.setPassword(newPassword);
        }
        ProductEntity pro = productService.getById(deviceEntity.getProductId());
        if (pro == null) {
            return R.error(200, "设备产品类型错误");
        }
        if (StringUtils.isEmpty(deviceEntity.getProtocol())) {
            deviceEntity.setProtocol(pro.getProtocol());
        }
        deviceEntity.setCrtTime(new Date());
        deviceEntity.setCrtUserId(getUserId() + "");
        deviceService.save(deviceEntity);

        return R.ok();
    }

    @SysLog("批量添加设备")
    @PostMapping("saveList")
    @RequiresPermissions("device:save")
    @ApiOperation(value = "批量加设备")
    public R saveList(@RequestBody List<DeviceEntity> deviceEntitys) {
        for (DeviceEntity d : deviceEntitys) {
            deviceService.save(d);
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改设备")
    @PutMapping("update")
    @RequiresPermissions("device:update")
    @ApiOperation(value = "修改")
    public R update(@RequestBody DeviceEntity deviceEntity) {
        deviceEntity.setUpdateTime(new Date());
        deviceEntity.setUpdateUserId(getUserId() + "");
        deviceService.updateById(deviceEntity);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除设备")
    @DeleteMapping("/delete")
    @RequiresPermissions("device:delete")
    @ApiOperation(value = "删除")
    public R<?> delete(String imei) {
        positionsService.remove(Wrappers.<PositionsEntity>query().lambda().eq(PositionsEntity::getImei, imei));
        deviceStatusService.removeByImei(imei);
        deviceService.removeById(imei);
        return R.ok();
    }

    @SysLog("重置设备登录密码")
    @RequiresPermissions("device:update")
    @PutMapping("resetPassword")
    @ApiOperation(value = "重置设备登录密码")
    public R<String> resetPassword(@RequestParam String imei) {
        DeviceEntity u = deviceService.getById(imei);
        if (u == null) {
            return R.error("无此设备");
        }
        String pwd = UIDGenerator.genRandomNum(4);
        //sha256加密
        String newPassword = DigestUtils.md5Hex(pwd);
//        String newPassword = TokenGenerator.generateValue(pwd);
        //更新密码
        boolean flag = deviceService.updatePassword(imei, u.getPassword(), newPassword);
        if (!flag) {
            return R.error("密码不正确");
        }
        return R.ok(pwd);
    }

}
