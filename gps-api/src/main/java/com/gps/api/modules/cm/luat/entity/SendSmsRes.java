package com.gps.api.modules.cm.luat.entity;

import lombok.Data;

@Data
public class SendSmsRes {
    @Data
    public static class Res {
        String msisdn;
        int sms_id;
    }
    Res sms;
}
