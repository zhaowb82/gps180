package com.gps.db.enums;

/**
 * 命令类型
 */
public enum ControlType {
    UNLOCKING("unlockType","开锁支持类型"),
    LOCKED("lockType", "关锁支持类型"),
    FINDCAR("carSearchType", "寻车支持类型"),
    INTERCEPTOPEN("parkingOilType", "设防(停车断油)支持类型"),
    INTERCEPTCLOSE("oilSupplyType", "撤防(通油)支持类型"),
    REBOOT("rebootDevice", "重启");


    private String value;

    private String desc;

    ControlType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
