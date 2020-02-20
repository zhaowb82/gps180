package com.gps.api.rbac;

/**
 * 数据权限接口类
 * @author Administrator
 *
 */
public interface DataAccess {
	 DataAccessResullt getOrg(Long userId, Long orgId);
	 String getName();
	 Integer getType();
	 
}
