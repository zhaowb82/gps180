/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.controller;

import com.gps.db.entity.DeviceEntity;
import com.gps.api.common.utils.ShiroUtils;
import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.api.modules.sys.oauth2.GpsPrincipal;

/**
 * Controller公共组件
 */
public abstract class AbstractController {

	protected GpsPrincipal getGpsPrincipal() {
		return ShiroUtils.getGpsPrincipal();
	}

	protected SysUserEntity getUser() {
		return ShiroUtils.getUserEntity();
	}

	protected DeviceEntity getDevice() {
		return ShiroUtils.getDeviceEntity();
	}

	protected Long getUserId() {
		return ShiroUtils.getUserId();
	}

	protected Long getDeptId() {
		return ShiroUtils.getDeptId();
	}
}
