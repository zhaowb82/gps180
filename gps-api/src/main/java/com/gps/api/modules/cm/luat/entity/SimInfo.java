package com.gps.api.modules.cm.luat.entity;

import lombok.Data;

import java.util.List;

@Data
public class SimInfo {
    @Data
    public static class Sims {
        int count;
        List<SimInfo> card_list;
    }
    String msisdn;
    String iccid;
    String imsi;
    String sp_code;//短信端口
    String carrier;//运营商
    int data_plan; //套餐大小
    float data_usage;// 当月用量,
    int account_status;// 卡状态,
    int expiry_date;// 计费结束日期,
    int active;// 激活/未激活,
    int test_valid_date;// 测试期起始日期,
    int silent_valid_date;// 沉默期起始日期,
    float test_used_data_usage;// 测试期已用流量,
    int active_date;// 激活日期,
    float data_balance;// 剩余流量,
    int outbound_date;// 出库日期,
    int support_sms;// 是否支持短信
}
