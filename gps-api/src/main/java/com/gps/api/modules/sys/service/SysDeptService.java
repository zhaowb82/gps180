package com.gps.api.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.api.modules.sys.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-20 16:41:58
 */
public interface SysDeptService extends IService<SysDeptEntity> {

    MyPage<SysDeptEntity> queryPage(Map<String, Object> params);
	
//	SysDeptEntity queryObject(Long deptId);

	List<SysDeptEntity> listByUser();
//
//	void saveSysDept(SysDeptEntity sysDept);
//
//	void update(SysDeptEntity sysDept);

	void delete(Long deptId);

	/**
	 * 查询子部门ID列表
	 * @param parentId  上级部门ID
	 */
	List<Long> queryDetpIdList(Long parentId);

	/**
	 * 获取子部门ID(包含本部门ID)，用于数据过滤
	 */
	String getSubDeptIdList(Long deptId);

}
