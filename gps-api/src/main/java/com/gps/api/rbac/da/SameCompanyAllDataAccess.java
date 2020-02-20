package com.gps.api.rbac.da;

import com.gps.api.rbac.tree.OrgItem;
import com.gps.api.rbac.AccessType;
import com.gps.api.rbac.CorePlatformService;
import com.gps.api.rbac.DataAccess;
import com.gps.api.rbac.DataAccessResullt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 同公司下所有
 *
 * @author lijiazhi
 */
@Component
public class SameCompanyAllDataAccess implements DataAccess {

    @Autowired
    CorePlatformService platformService;

    @Override
    public DataAccessResullt getOrg(Long userId, Long orgId) {
        DataAccessResullt ret = new DataAccessResullt();
        ret.setStatus(AccessType.OnlyOrg);
        OrgItem item = platformService.getCurrentOrgItem();
        OrgItem company = item.findParentOrgItem(DefaultDataAccessFactory.COMPANY_TYPE);
        if (company == null) {
            ret.setOrgIds(Collections.EMPTY_LIST);
            return ret;
        }
        List<OrgItem> all = company.findAllChildOrgItem(null, DefaultDataAccessFactory.COMPANY_TYPE);
        all.add(company);
        List<Long> list = new ArrayList<Long>(all.size());
        for (OrgItem org : all) {
            list.add(org.getId());
        }
        ret.setOrgIds(list);
        return ret;

    }

    @Override
    public String getName() {
        return "查看同公司(不含子公司)";
    }

    @Override
    public Integer getType() {
        return 2;
    }

}
