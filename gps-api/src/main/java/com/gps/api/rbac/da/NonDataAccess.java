package com.gps.api.rbac.da;

import com.gps.api.rbac.AccessType;
import com.gps.api.rbac.DataAccess;
import com.gps.api.rbac.DataAccessResullt;
import org.springframework.stereotype.Component;

@Component
public class NonDataAccess implements DataAccess {

	@Override
	public DataAccessResullt getOrg(Long userId, Long orgId) {
		DataAccessResullt ret = new DataAccessResullt();
		ret.setStatus(AccessType.NoneOrg);
		return ret;
		
	}

	@Override
	public String getName() {
		return "无权限";
	}

	@Override
	public Integer getType() {
		return 10;
	}

}
