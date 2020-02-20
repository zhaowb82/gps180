package com.gps.api.rbac;


import com.gps.api.rbac.tree.OrgItem;

import java.util.List;

public interface DataAccessFactory {
    /**
     * 得到数去访问权限
     *
     * @param type
     * @return
     */
    public DataAccess getDataAccess(Integer type);

    /**
     * 得到用户能看到得到组织机构树，比如，用户只能看到公司级别的组织机构树。
     * DefaultDataAccessFactory 默认实现了能看到所在公司（集团，母公司）的组织机构树
     * @return
     */
    public OrgItem getUserOrgTree(OrgItem item);


    public List<DataAccess> all();
}
