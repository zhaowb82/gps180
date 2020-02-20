/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 * <p>
 * https://www.gps180.com
 * <p>
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gps.api.ApiRedisUtils;
import com.gps.api.common.utils.ShiroUtils;
import com.gps.api.modules.sys.redis.SysUserTokenRedis;
import com.gps.api.modules.sys.service.SysUserTokenService;
import com.gps.api.modules.sys.service.impl.SysUserTokenServiceImpl;
import com.gps.common.helper.HumpUtil;
import com.gps.db.dbutils.Constant;
import com.gps.db.dbutils.MyPage;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.service.DeviceService;
import com.gps.db.service.UserGroupService;
import com.gps.api.common.annotation.SysLog;
import com.gps.db.utils.MapUtils;
import com.gps.db.utils.R;
import com.gps.api.common.validator.Assert;
import com.gps.api.common.validator.ValidatorUtils;
import com.gps.api.common.validator.group.AddGroup;
import com.gps.api.common.validator.group.UpdateGroup;
import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.api.modules.sys.form.PasswordForm;
import com.gps.api.modules.sys.service.SysUserRoleService;
import com.gps.api.modules.sys.service.SysUserService;
import com.gps.api.rbac.DataAccess;
import com.gps.api.rbac.da.DefaultDataAccessFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
@Api(tags = "A用户管理")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private DefaultDataAccessFactory defaultDataAccessFactory;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ApiRedisUtils apiRedisUtils;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    private void deleteRedisUser(Long userId) {
        String key = "redis-user-" + userId;
        apiRedisUtils.delete(key);
    }
    /**
     * 所有用户列表
     */
    @GetMapping("list")
    @RequiresPermissions("sys:user:list")
    @ApiOperation(value = "所有用户列表")
    public R<MyPage<SysUserEntity>> list(@RequestParam(required = false) Long pageNum,
									     @RequestParam(required = false) Long pageSize,
										 @RequestParam(required = false, defaultValue = "create_time") String sort,
										 @RequestParam(required = false, defaultValue = "DESC") String order,
									     @RequestParam(required = false) String username,
										 @RequestParam(required = false) String deptId,
									     @RequestParam(required = false) Date startDate,
									     @RequestParam(required = false) Date endDate) {
		MyPage<SysUserEntity> page = null;
		if (true) {
			page = sysUserService.queryPage2(pageNum, pageSize, HumpUtil.humpToLine(sort), order,
					username, deptId, startDate, endDate);
		} else {
			//只有超级管理员，才能查看所有管理员列表
			Long createUserId = null;
			if (getUserId() != Constant.SUPER_ADMIN) {
				createUserId = getUserId();
			}
			QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<SysUserEntity>()
					.like(StringUtils.isNotBlank(username), "username", username)
					.eq(createUserId != null, "create_user_id", createUserId);
			page = sysUserService.queryPage(pageNum, pageSize, sort, order, wrapper);
		}
        return R.ok(page);
    }

    @GetMapping(value = "users")
    @RequiresPermissions("sys:user:list")
    @ApiOperation(value = "显示自己的子公司帐号,选择时用")
    public R<List<SysUserEntity>> users() {
        List<SysUserEntity> list = sysUserService.listByUser();
        return R.ok(list);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    @ApiOperation("获取登录的用户信息")
    public R info() {
        return R.ok(getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    @ApiOperation(value = "修改登录用户密码")
    public R password(@RequestBody PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");

        if (getUser() != null) {
            //sha256加密
            String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
            //sha256加密
            String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

            //更新密码
            boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
            if (!flag) {
                return R.error("原密码不正确");
            }
            deleteRedisUser(getUserId());
        }
        if (getDevice() != null) {
            //sha256加密
            String password = DigestUtils.md5Hex(form.getPassword());
            String newPassword = DigestUtils.md5Hex(form.getNewPassword());
            //更新密码
            boolean flag = deviceService.updatePassword(getDevice().getImei(), password, newPassword);
            if (!flag) {
                return R.error("密码不正确");
            }
        }
        return R.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    @ApiOperation(value = "用户信息")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok(user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    @ApiOperation(value = "保存用户")
    public R save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        user.setCreateUserId(getUserId());
        if (user.getDeptId() == 0) {
            user.setDeptId(getDeptId());
        }
        sysUserService.saveUser(user);
        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @PutMapping("/update")
    @RequiresPermissions("sys:user:update")
    @ApiOperation(value = "修改用户")
    public R update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        user.setCreateUserId(getUserId());
        sysUserService.update(user);
        deleteRedisUser(user.getUserId());

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    @ApiOperation(value = "删除用户")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }

        for (Long userId : userIds) {
            sysUserRoleService.removeByMap(new MapUtils().put("user_id", userId));
            userGroupService.removeByMap(new MapUtils().put("user_id", userId));
            deleteRedisUser(userId);
        }
        sysUserService.deleteBatch(userIds);

        return R.ok();
    }
    //////////////////////////////////////////////////////////////////////////


    @SysLog("重置密码")
    @PutMapping("resetPassword")
    @ApiOperation(value = "重置密码")
    public R<String> resetPassword(@RequestParam Long userId) {
        SysUserEntity u = sysUserService.getById(userId);
        if (u == null) {
            return R.error("无此用户");
        }
        //sha256加密
        String newPassword = new Sha256Hash("123456", u.getSalt()).toHex();
        //更新密码
        boolean flag = sysUserService.updatePassword(u.getUserId(), u.getPassword(), newPassword);
        if (!flag) {
            return R.error("密码不正确");
        }
        deleteRedisUser(userId);
        sysUserTokenService.logoutById(userId);
        return R.ok("123456");
    }

    @SysLog("编辑个人信息")
    @PostMapping("editmyinfo")
    @ApiOperation(value = "编辑个人信息")
    public R<Boolean> editmyinfo(@RequestBody SysUserEntity user) {
        SysUserEntity u = sysUserService.queryByUserName(user.getUsername());
        if (u == null) {
            return R.error("无此用户");
        }
        u.setEmail(user.getEmail());
        u.setNickname(user.getNickname());
        u.setMobile(user.getMobile());
        u.setMultilogin(user.getMultilogin());
        boolean r = sysUserService.updateById(u);
        deleteRedisUser(user.getUserId());
        return R.ok(r);
    }

    //////////////////////////////////////////////////////////////
    @GetMapping(value = "datascopes")
//	@RequiresPermissions("sys:user:list")
    @ApiOperation(value = "数据权限")
    public R<List<DataAccess>> datascopes() {
        List<DataAccess> list = defaultDataAccessFactory.all();
        return R.ok(list);
    }
}
