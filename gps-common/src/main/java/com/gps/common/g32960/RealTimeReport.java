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
public class RealTimeReport {
    long recordTime;
    boolean reissue;
    VehicleState vehicleState;
    List<MotorState> motor;
    FuelCell fuelCell;
    Engine engine;
    VehicleLocation location;
    Extremum extremum;
    Alarm alarm;
    List<ChargeableSubsystemElectric> chargeSystemElectric;
    List<ChargeableSubsystemTemperature> chargeSystemTemperature;
    int customControlState;
}
