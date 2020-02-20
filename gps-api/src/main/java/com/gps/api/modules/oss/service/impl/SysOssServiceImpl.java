/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.oss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.api.modules.oss.dao.SysOssDao;
import com.gps.api.modules.oss.entity.SysOssEntity;
import com.gps.api.modules.oss.service.SysOssService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

	@Override
	public MyPage<SysOssEntity> queryPage(Map<String, Object> params) {
		IPage<SysOssEntity> page = this.page(
				MyQuery.getPage(params)
		);

		return MyPage.create(page);
	}
	
}
