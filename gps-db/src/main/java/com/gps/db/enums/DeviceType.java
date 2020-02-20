package com.gps.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum DeviceType {

    WIRED(0, "有线"),
    WIRELESS(1, "无线");

    private int index;
    private String desc;

    DeviceType(int index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public static DeviceType getIndex(String desc) {
        for (DeviceType type : DeviceType.values()) {
            if (StringUtils.equals(type.getDesc(), desc)) {
                return type;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
