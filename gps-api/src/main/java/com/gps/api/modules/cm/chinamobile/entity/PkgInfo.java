package com.gps.api.modules.cm.chinamobile.entity;

import lombok.Data;

@Data
public class PkgInfo {
    private String pkgCode;
    private String pkgName;
    private String subSprodId;
    private String totalFlow;
    private String usedFlow;
    private String totalUsedFlow;
    private String restFlow;
    private String pkgEfftDate;
    private String pkgExpireDate;
}
