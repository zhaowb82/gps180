package com.gps.api.modules.cm.chinamobile.entity;

import lombok.Data;

import java.util.List;

@Data
public class MsisdnInfo {
    private String msisdn;
    private String iccid;
    private String imsi;
    private String imei;
    private String time;//号码激活时间，测试期产品订购用户为沉默期到正使用时间，库存期产品订购用户为库存期到正使用时间，非测试期产品用户为开户时间
    private List<ApnInfo> apnList;
}
