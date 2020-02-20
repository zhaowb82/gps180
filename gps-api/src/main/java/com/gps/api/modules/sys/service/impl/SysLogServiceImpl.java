/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.service.impl;

import com.gps.api.modules.sys.dao.SysLogDao;
import com.gps.api.modules.sys.entity.SysLogEntity;
import com.gps.api.modules.sys.service.SysLogService;
import com.gps.db.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("sysLogService")
public class SysLogServiceImpl extends BaseServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

}
