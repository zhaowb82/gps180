package com.gps.api.rbac.da;

import com.gps.api.rbac.AccessType;
import com.gps.api.rbac.DataAccess;
import com.gps.api.rbac.DataAccessResullt;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 只查看自己
 * @author lijiazhi
 *
 */
@Component
public class OwnerDataAccess implements DataAccess {

	@Override
	public DataAccessResullt getOrg(Long userId, Long orgId) {
		DataAccessResullt ret = new DataAccessResullt();
		ret.setStatus(AccessType.OnlyUser);
		ret.setUserIds(Arrays.asList(userId));
		return ret;
		
	}

	@Override
	public String getName() {
		return "只查看自己";
	}

	@Override
	public Integer getType() {
		return 1;
	}

}
