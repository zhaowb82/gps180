/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.common.utils;

import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.api.modules.sys.oauth2.GpsPrincipal;
import com.gps.db.entity.DeviceEntity;
import com.gps.db.exception.RRException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
public class ShiroUtils {

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static GpsPrincipal getGpsPrincipal() {
		return (GpsPrincipal)SecurityUtils.getSubject().getPrincipal();
	}
	public static SysUserEntity getUserEntity() {
		GpsPrincipal gpsPrincipal = getGpsPrincipal();
		if (gpsPrincipal == null) {
			return null;
		}
		return gpsPrincipal.getUserEntity();
	}
	public static DeviceEntity getDeviceEntity() {
		GpsPrincipal gpsPrincipal = getGpsPrincipal();
		if (gpsPrincipal == null) {
			return null;
		}
		return gpsPrincipal.getDeviceEntity();
	}

	public static Long getUserId() {
		SysUserEntity en = getUserEntity();
		if (en == null) {
			return 2L;//设备专用帐号
		}
		return en.getUserId();
	}

	public static Long getDeptId() {
		SysUserEntity en = getUserEntity();
		if (en == null) {
			return null;
		}
		return en.getDeptId();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if(kaptcha == null){
			throw new RRException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}
}
