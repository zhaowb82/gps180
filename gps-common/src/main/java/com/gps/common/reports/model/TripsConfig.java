package com.gps.common.reports.model;

import lombok.Data;

@Data
public class TripsConfig {

    public TripsConfig() {
    }

    public TripsConfig(double minimalTripDistance, long minimalTripDuration, long minimalParkingDuration,
            long minimalNoDataDuration, boolean useIgnition, boolean processInvalidPositions, double speedThreshold) {
        this.minimalTripDistance = minimalTripDistance;
        this.minimalTripDuration = minimalTripDuration;
        this.minimalParkingDuration = minimalParkingDuration;
        this.minimalNoDataDuration = minimalNoDataDuration;
        this.useIgnition = useIgnition;
        this.processInvalidPositions = processInvalidPositions;
        this.speedThreshold = speedThreshold;
    }

    // 最短行程距离 M
    private double minimalTripDistance;
    // 最短行程时间 ms
    private long minimalTripDuration;
    // 最短停留点时间 ms
    private long minimalParkingDuration;
    // 最短无数据时间 ms
    private long minimalNoDataDuration;

    private boolean useIgnition;

    private boolean processInvalidPositions;

    private double speedThreshold;


    private long minimalParkTime;

}
