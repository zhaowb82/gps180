
package com.gps.common.reports.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class StopReport extends BaseReport  {

//    private long positionId;
    private double latitude;
    private double longitude;
    private Date startTime;
    private Date endTime;
    private String address;
    private long engineHours; // milliseconds
    public void addEngineHours(long engineHours) {
        this.engineHours += engineHours;
    }
}
