package com.gps.common.g32960;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//可充电储能子系统
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargeableSubsystemElectric {
    int chargeableSubSystemNumber;//可充电储能子系统号
    float voltage;//可充电储能装置电压
    float current;//可充电储能装置电流
    long batteryTotalCount;//单体电池总数
    long frameStartBatterySeq;//本帧起始电池序号
    List<Float> batteryVoltage;//单体电池电压值
}
