package com.gps.common.g32960;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//极值数据
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Extremum {
    int voltageMaxSubsystem;//最高电压电池子系统号
    int voltageMaxBattery;//最高电压电池单体代号
    float maxVoltage;//电池单体电压最高值
    int voltageMinSubsystem;//最低电压电池子系统号
    int voltageMinBattery;//最低电压电池单体代号
    float minVoltage;//电池单体电压最低值
    int temperatureMaxSubsystem;//最高温度子系统号
    int temperatureMaxProbe;//最高温度探针序号
    int maxTemperature;//最高温度值
    int temperatureMinSubsystem;//最低温度子系统号
    int temperatureMinProbe;//最低温度探针序号
    int minTemperature;//最低温度值
}
