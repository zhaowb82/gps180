/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 * <p>
 * https://www.gps180.com
 * <p>
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gps.api.modules.sys.entity.SysUserRoleEntity;
import com.gps.db.dbutils.Constant;
import com.gps.db.dbutils.MyPage;
import com.gps.api.common.annotation.SysLog;
import com.gps.db.utils.R;
import com.gps.api.common.validator.ValidatorUtils;
import com.gps.api.modules.sys.entity.SysRoleEntity;
import com.gps.api.modules.sys.service.SysRoleMenuService;
import com.gps.api.modules.sys.service.SysRoleService;
import com.gps.api.modules.sys.service.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/role")
@Api(tags = "A角色管理")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 角色列表
     */
    @GetMapping("list")
    @RequiresPermissions("sys:role:list")
    @ApiOperation(value = "角色列表")
    public R<MyPage<SysRoleEntity>> list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
//        if (getUserId() != Constant.SUPER_ADMIN) {
//            params.put("createUserId", getUserId());
//        }

        MyPage<SysRoleEntity> page = sysRoleService.queryPage(params);

        return R.ok(page);
    }

    /**
     * 角色列表
     */
    @GetMapping("select")
//    @RequiresPermissions("sys:role:select")
    @ApiOperation(value = "角色列表")
    public R<List<SysRoleEntity>> select() {
//        Map<String, Object> map = new HashMap<>();
//        //如果不是超级管理员，则只查询自己所拥有的角色列表
//        if (getUserId() != Constant.SUPER_ADMIN) {
//            map.put("create_user_id", getUserId());
//        }
//        List<SysRoleEntity> list = (List<SysRoleEntity>) sysRoleService.listByMap(map);
        List<SysRoleEntity> list;
        List<SysRoleEntity> listAll = sysRoleService.list();
        if (getUserId() == Constant.SUPER_ADMIN) {
            list = listAll;
        } else {
            List<SysUserRoleEntity> urlst = sysUserRoleService.list(
                    Wrappers.<SysUserRoleEntity>lambdaQuery().eq(SysUserRoleEntity::getUserId, getUserId()));
            List<Long> rs = urlst.stream().map(SysUserRoleEntity::getRoleId).collect(Collectors.toList());
            List<Long> rss = new ArrayList<>();
            for (Long r : rs) {
                addToList(rss, r);
                if (r == 1) {
                    for (SysRoleEntity e : listAll) {
                        addToList(rss, e.getRoleId());
                    }
                } else if (r == 2) {
                    for (SysRoleEntity e : listAll) {
                        if (e.getRoleId() > r) {
                            addToList(rss, e.getRoleId());
                        }
                    }
                } else if (r == 3) {
                } else if (r == 4) {
                } else if (r == 5) {
                    addToList(rss, 3L);
                    addToList(rss, 4L);
                    addToList(rss, 5L);
                    addToList(rss, 6L);
                } else if (r == 6) {
                }
            }
            rss.remove(10L);
            list = sysRoleService.listByIds(rss);
        }
        return R.ok(list);
    }

    private void addToList(List<Long> list, Long v) {
        int i = list.indexOf(v);
        if (i < 0) {
            list.add(v);
        }
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    @ApiOperation(value = "角色信息")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.getById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return R.ok(role);
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    @ApiOperation(value = "保存角色")
    public R save(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        sysRoleService.saveRole(role);

        return R.ok();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @PutMapping("/update")
    @RequiresPermissions("sys:role:update")
    @ApiOperation(value = "修改角色")
    public R update(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        sysRoleService.update(role);

        return R.ok();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    @ApiOperation(value = "删除角色")
    public R delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);

        return R.ok();
    }

//    @GetMapping("/roles")
//    @ApiOperation(value = "")
//    public R<?> roles() {
//        if (getUser() == null) {
//            return R.error("不是用户登录");
//        }
//
//        List<SysRoleEntity> list = null;
//        if (getUserId() == Constant.SUPER_ADMIN) {
//            list = sysRoleService.list(Wrappers.<SysRoleEntity>query()
//                    .lambda()
//                    .ge(SysRoleEntity::getRoleId, 1)
//                    .ne(SysRoleEntity::getRoleId, 10));
//        } else {
//            list = sysRoleService.list(Wrappers.<SysRoleEntity>query()
//                    .lambda()
//                    .ge(SysRoleEntity::getRoleId, 2)
//                    .ne(SysRoleEntity::getRoleId, 10));
//        }
//        return R.ok(list);
//    }

    @GetMapping("/userRoles")
    @ApiOperation(value = "")
    public R userRoles(String userId) {
        if (getUser() == null) {
            return R.error("不是用户登录");
        }
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(Long.parseLong(userId));
        return R.ok(roleIdList);
    }
}
