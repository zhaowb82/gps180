package com.gps.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 事件实体类
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Event extends Message {

    public Event(String type, String imei, String alarmId) {
        this(type, imei);
        setAlarmId(alarmId);
    }

    public Event(String type, String imei) {
        setType(type);
        setImei(imei);
        this.serverTime = new Date();
    }

    public Event() {
    }

    public static final String ALL_EVENTS = "allEvents";

    public static final String TYPE_COMMAND_RESULT = "commandResult";

    public static final String TYPE_DEVICE_ONLINE = "deviceOnline";
    public static final String TYPE_DEVICE_UNKNOWN = "deviceUnknown";
    public static final String TYPE_DEVICE_OFFLINE = "deviceOffline";

    public static final String TYPE_DEVICE_HEART = "deviceHeart";

    public static final String TYPE_DEVICE_MOVING = "deviceMoving";
    public static final String TYPE_DEVICE_STOPPED = "deviceStopped";

    public static final String TYPE_DEVICE_OVERSPEED = "deviceOverspeed";
    public static final String TYPE_DEVICE_FUEL_DROP = "deviceFuelDrop";

    public static final String TYPE_GEOFENCE_ENTER = "geofenceEnter";
    public static final String TYPE_GEOFENCE_EXIT = "geofenceExit";

    public static final String TYPE_ALARM = "alarm";

    public static final String TYPE_IGNITION_ON = "ignitionOn";
    public static final String TYPE_IGNITION_OFF = "ignitionOff";

    public static final String TYPE_MAINTENANCE = "maintenance";

    public static final String TYPE_TEXT_MESSAGE = "textMessage";

    public static final String TYPE_DRIVER_CHANGED = "driverChanged";

    private Date serverTime;
    private String geofenceId;
    private String alarmId;
    private String companyId;

}
