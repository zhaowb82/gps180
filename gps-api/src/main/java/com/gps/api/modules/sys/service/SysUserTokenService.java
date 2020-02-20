/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.utils.R;
import com.gps.api.modules.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createUserToken(long userId, boolean fromPhone);

	R createDeviceToken(String imei, boolean fromPhone);

	SysUserTokenEntity getById2(long id);

	/**
	 * 退出，修改token值
	 */
	void logout();

	void logoutById(Long userId);

    void checkExpiredToken();
}
