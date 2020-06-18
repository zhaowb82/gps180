package com.gps.api.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gps.api.modules.sys.entity.SysRoleDeptEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与部门对应关系
 */
@Mapper
public interface SysRoleDeptDao extends BaseMapper<SysRoleDeptEntity> {
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long roleId);
	int deleteBatch(Long[] roleIds);
}
