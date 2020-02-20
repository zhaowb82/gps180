package com.gps.api.modules.cm.luat.entity;

import lombok.Data;

@Data
public class UsageLog {
    Float data_usage;//		流量，单位M
    String iccid;//		iccid
    String msisdn;//	msisdn
}
