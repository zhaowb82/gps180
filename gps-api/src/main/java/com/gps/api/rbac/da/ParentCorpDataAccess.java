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
 * 母公司所有
 * @author lijiazhi
 *
 */
@Component
public class ParentCorpDataAccess implements DataAccess {
	
	@Autowired
	CorePlatformService platformService;

	@Override
	public DataAccessResullt getOrg(Long userId, Long orgId) {
		OrgItem root = platformService.buildOrg();
		OrgItem company = root.findAllChildOrgItem(DefaultDataAccessFactory.PARENT_CORP_TYPE).get(0);
		List<OrgItem> all = company.findAllChildOrgItem();
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
		return "母公司";
	}

	@Override
	public Integer getType() {
		return 7;
	}

}
