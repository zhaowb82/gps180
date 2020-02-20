package com.gps.common.model;

/**
 * 命令类型
 */
public enum CommandType {
    TYPE_SERVER_TRANS_TEXT("数据下行透传文本"),
    TYPE_SERVER_TRANS_DATA("数据下行透传16进制"),
    TYPE_SERVER_DEFENCE("手动设防"),
    TYPE_SERVER_UNDEFENCE("手动撤防"),
    TYPE_SERVER_LOCK_CAR("锁车"),
    TYPE_SERVER_UNLOCK_CAR("解锁车"),
    TYPE_SERVER_SET_RELAY_ELECTRICITY("远程断电控制"),
    TYPE_SERVER_SET_RELAY_OIL("远程断油控制"),
    TYPE_SERVER_LIGHT_SENSE_ALARM("光感报警开关"),
    TYPE_SERVER_REPORT_PERIOD("上报时间段"),
    TYPE_SERVER_TEMP_LOCATION("临时位置跟踪控制"),
    TYPE_SERVER_REPORT_INTERVAL_JH106("定时定位模式"),
    TYPE_SERVER_REPORT_INTERVAL("定时定位模式"),
    TYPE_SERVER_SET_SMART_MODE("智能上报模式"),
    TYPE_SERVER_SET_LOWBATTERY_ALARM("设置低电报警"),
    TYPE_SERVER_SET_SENSOR_ALARM("设置震动报警"),
    TYPE_SERVER_CONTROL_GPS("开关GPS"),
    TYPE_SERVER_FLIGHT_MODE("远程关机"),
    TYPE_SERVER_SET_SOS("设置SOS号码"),
    TYPE_SERVER_SET_CENTER_NUM("设置中心号码"),
    TYPE_SERVER_ONLINE_MODE("一直在线模式"),
    TYPE_SERVER_SUPER_STANDBY_MODE_H("超长待机模式"),
    TYPE_SERVER_RESTORE_FACTORY("恢复出厂设置"),
    TYPE_SERVER_SET_STATIC_MODE("省电模式"),
    TYPE_SERVER_SET_STATIC_MODE1("设置省电模式"),
    TYPE_SERVER_SET_STATIC_MODE2("省电模式"),
    TYPE_SERVER_SOUND_SENSOR("声控设置"),
    TYPE_SERVER_DIS_ALARM("解除报警"),
    TYPE_SERVER_ALARM_MODE("报警方式"),
    TYPE_SERVER_SCHEDULE_RESTART("关机后定时开机"),
    TYPE_SERVER_SUPER_STANDBY_MODE("超长待机模式"),
    TYPE_SERVER_32960_CONTROL("g32960Control"),
    TYPE_SERVER_32960_CUS_CONTROL("g32960CusControl"),
    TYPE_SERVER_32960_WARNING("g32960Warning"),
    TYPE_SERVER_32960_UPGRADE("g32960Upgrade"),
    TYPE_SERVER_32960_QUERY_CONFIG("g32960QueryConfig"),
    TYPE_SERVER_32960_SETUP_CONFIG("g32960SetupConfig"),
    TYPE_SERVER_808_QUERY_ATTR("查询终端属性"),
    TYPE_SERVER_808_QUERY_CONFIG("808QueryConfig"),
    TYPE_SERVER_808_SETUP_CONFIG("808SetupConfig"),
    TYPE_SERVER_POSITION_SINGLE("单次定位 808等"),

    TYPE_SERVER_RECORD_START("开始录音"),
    TYPE_SERVER_RECORD_STOP("结束录音"),
    TYPE_REBOOT_DEVICE("重启设备"),
    TYPE_CUSTOM("自定义用户命令"),

    TYPE_IMPORT_CMD("导入设备指令"),
    TYPE_UNKOWN("不知道"),
    ;

    private String desc;

    CommandType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
