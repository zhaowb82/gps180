package com.gps.api.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.Jt809UserEntity;
import com.gps.db.service.Jt809DevPlatService;
import com.gps.db.service.Jt809UserService;
import com.gps.db.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2019-06-26 09:26:45
 */
@RestController
@RequestMapping("jt809")
@Api(tags = "GPS809管理")
public class Jt809UserController {
    @Autowired
    private Jt809UserService jt809UserService;
    @Autowired
    private Jt809DevPlatService jt809DevPlatService;

    /**
     * 列表
     */
    @GetMapping("list")
    @RequiresPermissions("jt809:list")
    @ApiOperation(value = "列表")
    public R list(@RequestParam(required = false) Long pageNum,
                  @RequestParam(required = false) Long pageSize,
                  @RequestParam(required = false, defaultValue = "user_type") String sort,
                  @RequestParam(required = false, defaultValue = "DESC") String order) {
        QueryWrapper<Jt809UserEntity> wrapper = new QueryWrapper<>();
        MyPage<Jt809UserEntity> page = jt809UserService.queryPage(pageNum, pageSize, sort, order, wrapper);

        return R.ok(page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("jt809:info")
    @ApiOperation(value = "信息")
    public R info(@PathVariable("id") String id) {
        Jt809UserEntity jt809User = jt809UserService.getById(id);

        return R.ok(jt809User);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("jt809:save")
    @ApiOperation(value = "添加")
    public R save(@RequestBody Jt809UserEntity jt809User) {
        jt809UserService.save(jt809User);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @RequiresPermissions("jt809:update")
    @ApiOperation(value = "修改")
    public R update(@RequestBody Jt809UserEntity jt809User) {
        jt809UserService.updateById(jt809User);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @RequiresPermissions("jt809:delete")
    @ApiOperation(value = "删除")
    public R delete(@RequestParam String[] ids) {
        jt809UserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @PostMapping("addDevice")
    @ApiOperation(value = "添加设备")
    public R addDevice(@RequestParam String upPlatId, @RequestParam String imei) {
        Jt809UserEntity u = jt809UserService.getById(upPlatId);
        if (u == null) {
            return R.error("没有此上级平台");
        }
        if (!StringUtils.equals(u.getUserType(), "UP_USER")) {
            return R.error("此平台不是上级平台");
        }
//        jt809DevPlatService.saveBatch();
        return R.ok();
    }

    @PostMapping("delDevice")
    @ApiOperation(value = "删除设备")
    public R delDevice(@RequestParam String[] ids) {
        jt809UserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


}
