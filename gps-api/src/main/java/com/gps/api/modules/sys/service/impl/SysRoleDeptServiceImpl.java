package com.gps.api.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gps.api.modules.sys.dao.SysRoleDeptDao;
import com.gps.api.modules.sys.entity.SysRoleDeptEntity;
import com.gps.api.modules.sys.service.SysRoleDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 角色与部门对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年6月21日 23:42:30
 */
@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptDao, SysRoleDeptEntity> implements SysRoleDeptService {

	@Override
	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
		deleteBatch(new Long[]{roleId});

		if(deptIdList.size() == 0){
			return ;
		}

		for(Long depId : deptIdList){
			SysRoleDeptEntity entity = new SysRoleDeptEntity();
			entity.setDeptId(roleId);
			entity.setRoleId(roleId);
			this.save(entity);
		}
	}

	@Override
	public List<Long> queryDeptIdList(Long roleId) {
		return baseMapper.queryDeptIdList(roleId);
	}

	@Override
	public int deleteBatch(Long[] roleIds) {
		return baseMapper.deleteBatch(roleIds);
	}

}
