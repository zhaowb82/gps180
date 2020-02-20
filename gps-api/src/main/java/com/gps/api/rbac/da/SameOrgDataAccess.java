package com.gps.api.rbac.da;

import com.gps.api.rbac.AccessType;
import com.gps.api.rbac.DataAccess;
import com.gps.api.rbac.DataAccessResullt;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 同机构
 * @author lijiazhi
 *
 */
@Component
public class SameOrgDataAccess implements DataAccess {
	
	@Override
	public DataAccessResullt getOrg(Long userId, Long orgId) {
		DataAccessResullt ret = new DataAccessResullt();
		ret.setStatus(AccessType.OnlyOrg);
		ret.setOrgIds(Arrays.asList(orgId));
		return ret;
		
	}

	@Override
	public String getName() {
		return "同结构(公司) (不含子公司)";
	}

	@Override
	public Integer getType() {
		return 3;
	}

}
