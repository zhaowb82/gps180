package com.gps.common.g32960;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//可充电储能装置温度
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargeableSubsystemTemperature {
    int subSystemNumber;//可充电储能子系统号
    List<Integer> probeTemperature;//可充电储能子系统各温度探针检测到的温度值
}
