package com.gps.api.rbac.datascope.cond;

import com.gps.db.datascope.PermissionAop;
import com.gps.db.entity.DeviceEntity;
import com.gps.api.common.utils.SpringContextUtils;
import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.api.modules.sys.oauth2.GpsPrincipal;
import com.gps.api.rbac.DataAccess;
import com.gps.api.rbac.DataAccessResullt;
import com.gps.api.rbac.da.DefaultDataAccessFactory;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

public class UserConditionUtils {
    public static List<TableCondition> getTableCondition(Table tableName, PermissionAop permissionAop) {
        GpsPrincipal principal = null;
        try {
            principal = (GpsPrincipal) SecurityUtils.getSubject().getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (principal != null) {
            SysUserEntity u = principal.getUserEntity();
            DeviceEntity d = principal.getDeviceEntity();
            if (u != null) {
                Long depId = u.getDeptId();
                return getTableConditionForUser(tableName, permissionAop, depId, u.getUserId(), u.getDataScopeType());
            }
            if (d != null) {
                Long depId = Long.parseLong(d.getCompanyId());
                String imei = d.getImei();
                return getTableConditionForDevice(tableName, permissionAop, depId, imei, d);
            }
        }
        return null;
    }

    private static List<TableCondition> getTableConditionForUser(Table table, PermissionAop permissionAop, Long depId, Long userId, Integer dataScopeType) {
        boolean filter = true;
        boolean onlyU = permissionAop.user();
        String taAlis = permissionAop.tableAlias();
        if (StringUtils.isNotEmpty(taAlis)) {
            filter = false;
            Alias alis = table.getAlias();
            if (alis != null) {
                if (StringUtils.equals(alis.getName(), taAlis)) {
                    filter = true;
                }
            }
        }

        String tableName = table.getFullyQualifiedName();

        List<TableCondition> l = new ArrayList<>();
        if (filter) {
            DefaultDataAccessFactory da = SpringContextUtils.getBean(DefaultDataAccessFactory.class);

            DataAccess daa = da.getDataAccess(dataScopeType);
            if (daa == null && userId == 1) {
                daa = da.getAllOrg();
            }
            if (daa != null) {
                DataAccessResullt dttt = daa.getOrg(userId, depId);
                switch (dttt.getStatus()) {
                    case OnlyUser:
                        processUserData(l, tableName, dttt.getUserIds());
                        break;
                    case OnlyOrg:
                        processOrgData(l, tableName, dttt.getOrgIds());
                        break;
                    case AllOrg:
                        break;
                    case NoneOrg:
                        l.add(new TableCondition("=", "1", "0"));
                        break;
                }
            } else {
                l.add(new TableCondition("=", "1", "0"));
            }
        }
        return l;
    }

    private static void processOrgData(List<TableCondition> l, String tableName, List<Long> depIds) {
        if (tableName.equalsIgnoreCase("device")) {
            l.add(new TableCondition("in", "company_id", depIds));
        }
        if (tableName.equalsIgnoreCase("alarm")) {
            l.add(new TableCondition("in", "company_id", depIds));
        }
        if (tableName.equalsIgnoreCase("sys_dept")) {
            l.add(new TableCondition("in", "dept_id", depIds));
        }
        if (tableName.equalsIgnoreCase("t_groups")) {
            l.add(new TableCondition("in", "dept_id", depIds));
        }
        if (tableName.equalsIgnoreCase("sys_user")) {
            l.add(new TableCondition("in", "dept_id", depIds));
        }
    }

    private static void processUserData(List<TableCondition> l, String tableName, List<Long> userIds) {
        if (tableName.equalsIgnoreCase("device")) {
            l.add(new TableCondition("in", "crt_user_id", userIds));
        }
        if (tableName.equalsIgnoreCase("alarm")) {
            l.add(new TableCondition("=", "1", "0"));
        }
        if (tableName.equalsIgnoreCase("sys_dept")) {
            l.add(new TableCondition("=", "1", "0"));
        }
        if (tableName.equalsIgnoreCase("t_groups")) {
            l.add(new TableCondition("in", "crt_user_id", userIds));
        }
        if (tableName.equalsIgnoreCase("sys_user")) {
            l.add(new TableCondition("in", "create_user_id", userIds));
        }
    }

    private static List<TableCondition> getTableConditionForDevice(Table table, PermissionAop permissionAop, Long depId, String imei, DeviceEntity d) {
        String tableName = table.getFullyQualifiedName();
        List<TableCondition> l = new ArrayList<>();
        if (tableName.equalsIgnoreCase("device")) {
            l.add(new TableCondition("=", "imei", imei));
        }
        if (tableName.equalsIgnoreCase("alarm")) {
            l.add(new TableCondition("=", "imei", imei));
        }
        if (tableName.equalsIgnoreCase("device_status")) {
            l.add(new TableCondition("=", "imei", imei));
        }
        if (tableName.equalsIgnoreCase("t_groups")) {
//            l.add(new TableCondition("=", "dept_id", d.getCompanyId()));
            l.add(new TableCondition("=", "id", d.getGroupId()));
        }
        if (tableName.equalsIgnoreCase("sys_dept")) {
            l.add(new TableCondition("=", "1", "0"));
        }
        if (tableName.equalsIgnoreCase("sys_user")) {
            l.add(new TableCondition("=", "1", "0"));
        }
        return l;
    }
}
