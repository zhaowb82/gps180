package com.gps.common.reports.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class TripReport extends BaseReport {

//    private long startPositionId;
//    private long endPositionId;
    private double startLat;
    private double startLon;
    private Date startTime;
    private String startAddress;
    private double startDistance;

    private double endLat;
    private double endLon;
    private Date endTime;
    private String endAddress;
    private double endDistance;

    private long duration;
//    private String driverUniqueId;
//    private String driverName;
}
