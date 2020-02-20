package com.gps.api.modules.cm.chinamobile.entity;

import lombok.Data;

@Data
public class GroupInfo {
    private String groupName;
    private Integer totalCount;
    private Integer testCount;
    private Integer silentCount;
    private Integer stockCount;
    private Integer normalCount;
    private Integer preCloseCount;
    private Integer bespeakCloseCount;
    private Integer stopCount;

    private Integer inventoryCount;
}
