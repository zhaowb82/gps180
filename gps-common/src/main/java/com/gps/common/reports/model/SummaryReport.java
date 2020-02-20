package com.gps.common.reports.model;

public class SummaryReport extends BaseReport {

    private long engineHours; // milliseconds

    public long getEngineHours() {
        return engineHours;
    }

    public void setEngineHours(long engineHours) {
        this.engineHours = engineHours;
    }

    public void addEngineHours(long engineHours) {
        this.engineHours += engineHours;
    }
}
