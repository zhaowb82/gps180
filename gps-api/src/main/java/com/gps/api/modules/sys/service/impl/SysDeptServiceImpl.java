package com.gps.api.modules.sys.service.impl;

import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiniu.util.StringUtils;
import com.gps.api.modules.sys.dao.SysDeptDao;
import com.gps.api.modules.sys.entity.SysDeptEntity;
import com.gps.api.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {
	@Autowired
	private SysDeptDao sysDeptDao;
	
//	@Override
//	public SysDeptEntity queryObject(Long deptId){
//		return sysDeptDao.queryObject(deptId);
//	}
    @Override
    public MyPage<SysDeptEntity> queryPage(Map<String, Object> params) {
        IPage<SysDeptEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<SysDeptEntity>()
        );

		return MyPage.create(page);
    }
	
	@Override
//	@DataFilter(tableAlias = "d", user = false)
	public List<SysDeptEntity> listByUser(){
    	return sysDeptDao.listByUser();
	}
	
//	@Override
//	public void saveSysDept(SysDeptEntity sysDept){
//		this.save(sysDept);
////		return false;
//	}
	
//	@Override
//	public void update(SysDeptEntity sysDept){
//		sysDeptDao.update(sysDept);
//	}
	
	@Override
	public void delete(Long deptId){
//		sysDeptDao.deleteById(deptId);
		removeById(deptId);
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return sysDeptDao.queryDetpIdList(parentId);
	}

	@Override
	public String getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		//添加本部门
		deptIdList.add(deptId);

		String deptFilter = StringUtils.join(deptIdList, ",");
		return deptFilter;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		for(Long deptId : subIdList){
			List<Long> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}
}
