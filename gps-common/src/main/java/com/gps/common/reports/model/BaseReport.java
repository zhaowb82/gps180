package com.gps.common.reports.model;

import lombok.Data;

@Data
public class BaseReport {

    private String deviceId;
    private String deviceName;
    private double distance;
    public void addDistance(double distance) {
        this.distance += distance;
    }

    private double averageSpeed;
    private double maxSpeed;
    public void setMaxSpeed(double maxSpeed) {
        if (maxSpeed > this.maxSpeed) {
            this.maxSpeed = maxSpeed;
        }
    }
    private long duration;

    private double spentFuel;
    private double startOdometer;
    private double endOdometer;
}
