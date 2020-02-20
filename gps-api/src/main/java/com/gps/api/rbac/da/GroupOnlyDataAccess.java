package com.gps.api.rbac.da;

import com.gps.api.rbac.tree.OrgItem;
import com.gps.api.rbac.AccessType;
import com.gps.api.rbac.CorePlatformService;
import com.gps.api.rbac.DataAccess;
import com.gps.api.rbac.DataAccessResullt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 集团公司所有，但不包含集团下的公司,仅仅集团职能部门
 * @author lijiazhi
 *
 */
@Component
public class GroupOnlyDataAccess implements DataAccess {
	
	@Autowired
	CorePlatformService platformService;

	@Override
	public DataAccessResullt getOrg(Long userId, Long orgId) {
		OrgItem root = platformService.buildOrg();
		OrgItem company  = root.findChild(orgId);
		OrgItem group = root.findParentOrgItem(DefaultDataAccessFactory.GROUP_TYPE);
		//排除集团下的所有公司
		List<OrgItem> all = group.findAllChildOrgItem(null,DefaultDataAccessFactory.COMPANY_TYPE);
		List<Long> list = new ArrayList<Long>(all.size());
		for(OrgItem org:all){
			list.add(org.getId());
		}
		
		DataAccessResullt ret = new DataAccessResullt();
		ret.setStatus(AccessType.OnlyOrg);
		ret.setOrgIds(list);
		return ret;
		
	}

	@Override
	public String getName() {
		return "仅集团部门";
	}

	@Override
	public Integer getType() {
		return 8;
	}

}
