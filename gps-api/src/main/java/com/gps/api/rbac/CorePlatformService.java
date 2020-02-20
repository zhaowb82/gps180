package com.gps.api.rbac;

import com.gps.api.rbac.tree.OrgItem;
import org.springframework.stereotype.Service;

/**
 * 系统平台功能访问入口，所有方法应该支持缓存或者快速访问
 * @author xiandafu
 */
@Service
public class CorePlatformService {
    public OrgItem buildOrg() {
//        CoreOrg root = sysOrgDao.getRoot();
//        OrgItem rootItem = new OrgItem(root);
//        CoreOrg org = new CoreOrg();
//        org.setDelFlag(DelFlagEnum.NORMAL.getValue());
//        List<CoreOrg> list = sysOrgDao.template(org);
//        OrgBuildUtil.buildTreeNode(rootItem,list);
//        //集团
//        return rootItem;
        return null;
    }

    public OrgItem getCurrentOrgItem() {
        //@TODO 无法缓存orgItem，因为组织机构在调整
        OrgItem root = buildOrg();
        OrgItem item = root.findChild(getCurrentOrgId());
        if (item == null) {
//            throw new PlatformException("未找到组织机构");
        }
        return item;
//        return null;
    }

    public Long getCurrentOrgId() {
//        checkSession();
//        CoreOrg org = (CoreOrg) httpRequestLocal.getSessionValue(ACCESS_CURRENT_ORG);
//        return org.getId();
        return 0L;
    }
}
