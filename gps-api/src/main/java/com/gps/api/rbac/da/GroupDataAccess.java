package com.gps.api.rbac.da;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gps.api.modules.sys.entity.SysDeptEntity;
import com.gps.api.modules.sys.service.SysDeptService;
import com.gps.api.rbac.AccessType;
import com.gps.api.rbac.DataAccess;
import com.gps.api.rbac.DataAccessResullt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 集团公司所有
 *
 * @author lijiazhi
 */
@Component
public class GroupDataAccess implements DataAccess {

//    @Autowired
//    CorePlatformService platformService;

    @Autowired
    private SysDeptService sysDeptService;

    private void getAllDept(List<SysDeptEntity> depsOut, SysDeptEntity dep, SysDeptService sysDeptService) {
        List<SysDeptEntity> l = sysDeptService.list(Wrappers.<SysDeptEntity>query().lambda().eq(SysDeptEntity::getParentId, dep.getDeptId()));
        if (l == null) {
            return;
        }
        for (SysDeptEntity d : l) {
            depsOut.add(d);
            getAllDept(depsOut, d, sysDeptService);
        }
    }

    @Override
    public DataAccessResullt getOrg(Long userId, Long orgId) {
//        OrgItem root = platformService.buildOrg();
//        OrgItem company = root.findChild(orgId);
//        OrgItem group = root.findParentOrgItem(DefaultDataAccessFactory.GROUP_TYPE);
//        List<OrgItem> all = group.findAllChildOrgItem();
//        List<Long> list = new ArrayList<>(all.size());
//        for (OrgItem org : all) {
//            list.add(org.getId());
//        }
        List<SysDeptEntity> depsOut = new ArrayList<>();
        SysDeptEntity depCur = sysDeptService.getById(orgId);
        depsOut.add(depCur);
        getAllDept(depsOut, depCur, sysDeptService);
        List<Long> depIds = depsOut.stream().map(SysDeptEntity::getDeptId).collect(Collectors.toList());
        DataAccessResullt ret = new DataAccessResullt();
        ret.setStatus(AccessType.OnlyOrg);
        ret.setOrgIds(depIds);
        return ret;
    }

    @Override
    public String getName() {
//        return "集团下所有";
        return "公司下所有 (包括子公司)";
    }

    @Override
    public Integer getType() {
        return 6;
    }

}
