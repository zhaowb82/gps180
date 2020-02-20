package com.gps.api.modules.cm.chinamobile.entity;

import lombok.Data;

import java.util.List;

@Data
public class ApnInfo {
    @Data
    public static class PlgInfoList {
        private List<PkgInfo> list;
    }
    @Data
    public static class MonthlyFlowList {
        private List<MonthlyFlow> list;
    }
    @Data
    public static class ApnInfoList {
        private List<ApnInfo> apnInfo;
    }
    private String apnName;
    private String totalFlow;
    private String usedFlow;
    private String restFlow;
    private String extraPkgFlow;
    private String lastFlowTime;
    private PlgInfoList pkgInfoList;
    private MonthlyFlowList monthlyList;
}
