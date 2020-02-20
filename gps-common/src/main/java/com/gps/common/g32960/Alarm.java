package com.gps.common.g32960;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//报警数据
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {
    int maxAlarmLevel;
    int alarmBitIdentify;
    int batteryFaultNum;
    List<Integer> batteryFaultData;
    int motorFaultNum;
    List<Integer> motorFaultData;
    int engineFaultNum;
    List<Integer> engineFaultData;
    int otherFaultNum;
    List<Integer> otherFaultData;

}
