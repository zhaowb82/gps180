/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.db.datascope.PermissionAop;
import com.gps.db.entity.CommandLogsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

	@PermissionAop(tableAlias = "su")
    List<SysUserEntity> listByUser();

	@PermissionAop(tableAlias = "su")
    IPage<SysUserEntity> queryPage2(@Param("page") IPage<SysUserEntity> page,
									@Param("username") String username, @Param("deptId") String deptId,
									@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
