package com.gps.api.rbac.da;

import com.gps.api.rbac.AccessType;
import com.gps.api.rbac.DataAccess;
import com.gps.api.rbac.DataAccessResullt;
import org.springframework.stereotype.Component;

/**
 * 所有机构
 * @author lijiazhi
 *
 */
@Component
public class AllGroupAccess implements DataAccess {
	
	@Override
	public DataAccessResullt getOrg(Long userId, Long orgId) {
		DataAccessResullt ret = new DataAccessResullt();
		ret.setStatus(AccessType.AllOrg);
		return ret;
	}

	@Override
	public String getName() {
		return "所有机构";
	}

	@Override
	public Integer getType() {
		return 5;
	}

}
