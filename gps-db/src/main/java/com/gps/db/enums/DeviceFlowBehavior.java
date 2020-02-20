package com.gps.common.enums;

import lombok.Getter;

/**
 * 设备流转行为
 *
 * @author haibin.tang
 * @create 2019-05-17 3:55 PM
 **/
@Getter
public enum  DeviceFlowBehavior {
    excel("导入"),
    a2b("出售"),
    a2c_c2c("转库"),
    replace_up("替换上"),
    replace_down("替换下"),
    install("安装"),
    c2a("退库"),
    loss("报失"),
    ;

    private String desc;

    DeviceFlowBehavior(String desc) {
        this.desc = desc;
    }
}
