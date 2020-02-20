package com.gps.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Command extends Message implements Cloneable {

    public static final String TYPE_CUSTOM = "custom";
    public static final String TYPE_POSITION_SINGLE = "positionSingle";
    public static final String TYPE_POSITION_STOP = "positionStop";
    public static final String TYPE_ENGINE_STOP = "engineStop";
    public static final String TYPE_ENGINE_RESUME = "engineResume";
    public static final String TYPE_ALARM_ARM = "alarmArm";
    public static final String TYPE_ALARM_DISARM = "alarmDisarm";
    public static final String TYPE_SET_TIMEZONE = "setTimezone";
    public static final String TYPE_REQUEST_PHOTO = "requestPhoto";
    public static final String TYPE_POWER_OFF = "powerOff";
    public static final String TYPE_REBOOT_DEVICE = "rebootDevice";
    public static final String TYPE_SEND_SMS = "sendSms";
    public static final String TYPE_SEND_USSD = "sendUssd";
    public static final String TYPE_SOS_NUMBER = "sosNumber";
    public static final String TYPE_SILENCE_TIME = "silenceTime";
    public static final String TYPE_SET_PHONEBOOK = "setPhonebook";
    public static final String TYPE_MESSAGE = "message";
    public static final String TYPE_VOICE_MESSAGE = "voiceMessage";
    public static final String TYPE_OUTPUT_CONTROL = "outputControl";
    public static final String TYPE_VOICE_MONITORING = "voiceMonitoring";
    public static final String TYPE_SET_AGPS = "setAgps";
    public static final String TYPE_SET_INDICATOR = "setIndicator";
    public static final String TYPE_CONFIGURATION = "configuration";
    public static final String TYPE_GET_VERSION = "getVersion";
    public static final String TYPE_FIRMWARE_UPDATE = "firmwareUpdate";
    public static final String TYPE_SET_CONNECTION = "setConnection";
    public static final String TYPE_SET_ODOMETER = "setOdometer";
    public static final String TYPE_GET_MODEM_STATUS = "getModemStatus";
    public static final String TYPE_GET_DEVICE_STATUS = "getDeviceStatus";


    //查询蓝牙设备名称
    public static final String TYPE_QUERY_BLUETOOTH_NAME = "queryBluetoothName";
    //查询蓝牙设备鉴权码
    public static final String TYPE_QUERY_BLUETOOTH_AUTH = "queryBluetoothAuth";
    //查询蓝牙设备Mac地址
    public static final String TYPE_QUERY_BLUETOOTH_MAC = "queryBluetoothMac";
    //移除在线设备session
    public static final String TYPE_REMOVE_SESSION = "removeSession";

    public static final String TYPE_MODE_POWER_SAVING = "modePowerSaving";
    public static final String TYPE_MODE_DEEP_SLEEP = "modeDeepSleep";

    public static final String TYPE_ALARM_GEOFENCE = "movementAlarm";
    public static final String TYPE_ALARM_BATTERY = "alarmBattery";
    public static final String TYPE_ALARM_SOS = "alarmSos";
    public static final String TYPE_ALARM_REMOVE = "alarmRemove";
    public static final String TYPE_ALARM_CLOCK = "alarmClock";
    public static final String TYPE_ALARM_SPEED = "alarmSpeed";
    public static final String TYPE_ALARM_FALL = "alarmFall";
    public static final String TYPE_ALARM_VIBRATION = "alarmVibration";

    public static final String KEY_UNIQUE_ID = "uniqueId";
    public static final String KEY_INTERVAL = "interval";
    public static final String KEY_TIMEZONE = "timezone";
    public static final String KEY_DEVICE_PASSWORD = "devicePassword";
    public static final String KEY_RADIUS = "radius";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_ENABLE = "enable";
    public static final String KEY_DATA = "data";
    public static final String KEY_INDEX = "index";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_SERVER = "server";
    public static final String KEY_IP = "ip";
    public static final String KEY_PORT = "port";
    public static final String KEY_ISHEX = "isHex";

    public static final String KEY_LIGHTSENSE = "lightsense";
    public static final String KEY_GPS = "gps";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_PAR_SEL = "sel";
    public static final String KEY_SPEED_LIMIT = "speedlimit";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_REPORT_INTERVAL = "reportInterval";
    //type = cmdcode
    private String id;//for log
    private String password;
    private String productId;
    private List<String> params;
    private boolean sync;//同步命令

    private int serialNum;

    public void setType(CommandType ct) {
        setType(ct.name());
    }

    public CommandType getCommandType() {
        try {
            return CommandType.valueOf(getType());
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
        }
        return CommandType.TYPE_UNKOWN;
    }

//    @Override
//    public Command clone() throws CloneNotSupportedException {
//        return (Command) super.clone();
//    }

}
