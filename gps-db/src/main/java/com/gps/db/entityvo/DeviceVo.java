package com.gps.db.entityvo;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class DeviceVo {
    private String imei;
    private String sn;
    private String sim;
    private Date simDeadline;
    private String protocol;
    private Date crtTime;
    private Date lastUpdateTime;
    private String productId;
    private String plateNo;
    private Integer deviceType;
    private String carVin;
    private String deviceDriver;
    private String rentId;
    private String crtUserId;
    private String groupId;
    private Boolean disabled;
    private String attr1;
    private String attr2;
    private String attr3;
    private String attr4;
    private String bluetoothName;
    private String bluetoothAuth;
    private String bluetoothMacAddress;
    private Integer status;

    private Double distance;

    //postion status
    private double latitude;
    private double longitude;
    private Integer speed;
    private Integer direction;
    private String connectionStatus;
    //    private Date lastUpdateTime;
    private String geofenceIds;
    private Integer geofenceStatus;
    private Integer blockStatus;
    private Integer alarmStatus;
    private Map<String, Object> attribute;
    private String rentName;
}
