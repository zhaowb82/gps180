package com.gps.db.enums;

/**
 * 设备状态
 *
 * @author haibin.tang
 * @create 2018-10-24 3:59 PM
 **/
public enum DeviceStatusEnum {

    BIND(1),
    UNBIND(0);

    private int value;

    DeviceStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
