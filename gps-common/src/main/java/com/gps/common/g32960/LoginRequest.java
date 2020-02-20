package com.gps.common.g32960;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    long recordTime;
    int loginDaySeq;
    String iccid;
    int systemCodeLength;
    List<String> chargeableSubsystemCode;

    public int getChargeableSubsystemCodeCount() {
        if (chargeableSubsystemCode == null) {
            return 0;
        }
        return chargeableSubsystemCode.size();
    }
}
