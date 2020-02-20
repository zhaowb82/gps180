package com.gps.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceState implements Serializable {

    private Boolean motionState;
    private Position motionPosition;

    private Boolean overspeedState;
    private Position overspeedPosition;
    private String overspeedGeofenceId;

}
