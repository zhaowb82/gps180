/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.controller;

import com.gps.db.entity.DeviceEntity;
import com.gps.db.service.DeviceService;
import com.gps.db.utils.R;
import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.api.modules.sys.form.SysLoginForm;
import com.gps.api.modules.sys.service.SysCaptchaService;
import com.gps.api.modules.sys.service.SysUserService;
import com.gps.api.modules.sys.service.SysUserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@Api
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;
	@Autowired
	private DeviceService deviceService;

	/**
	 * 验证码
	 */
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid)throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@PostMapping("/sys/login")
	@ApiOperation("登录")
	public R login(@RequestBody SysLoginForm form)throws IOException {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
//			return R.error("验证码不正确");
		}
		String from = form.getFrom();
		boolean fromPhone = true;
		if (StringUtils.isEmpty(from) || StringUtils.equals(from, "web")) {
			fromPhone = false;
		}
		if (StringUtils.isEmpty(form.getType()) || StringUtils.equals(form.getType(), "USER")) {
			//用户信息
			SysUserEntity user = sysUserService.queryByUserName(form.getUsername());
			//账号不存在、密码错误
			if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
				return R.error("账号或密码不正确");
			}
			//账号锁定
			if (user.getStatus() == 0) {
				return R.error("账号已被锁定,请联系管理员");
			}
			//生成token，并保存到数据库
			R r = sysUserTokenService.createUserToken(user.getUserId(), fromPhone);
			return r;
		}
		if (StringUtils.equals(form.getType(), "DEVICE")) {
			DeviceEntity dev = deviceService.getById(form.getUsername());
			String pas = DigestUtils.md5Hex(form.getPassword());
			if (dev == null || !StringUtils.equals(dev.getPassword(), pas)) {
				return R.error("设备号或密码不正确");
			}
			if (!dev.getCanLogin()) {
				return R.error("该设备没权限登录,请联系管理员");
			}
			//生成token，并保存到数据库
			R r = sysUserTokenService.createDeviceToken(dev.getImei(), fromPhone);
			return r;
		}
		return null;
	}


	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	@ApiOperation("退出")
	public R logout() {
		sysUserTokenService.logout();
		return R.ok();
	}
	
}
