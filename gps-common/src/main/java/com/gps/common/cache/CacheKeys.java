package com.gps.common.cache;

public final class CacheKeys {
    private CacheKeys() {
    }

    /**
     * 设备状态
     * @param uniqueId
     * @return
     */
    public static String getDeviceByUniqueId(String uniqueId) {
        return "devst_imei:" + uniqueId;
    }

    public static String getDeviceStateByDeviceId(String imei) {
        return "DeviceState_imei:" + imei;
    }

    /**
     * 命令
     * @param type
     * @param imei
     * @return
     */
    public static String getCommandKeys(String type, String imei) {
        return "cmd-" + type + "-" + imei;
    }

    /**
     * 命令回调
     * @param imei
     * @param serial
     * @return
     */
    public static String getFeedbackKeys(String imei, int serial) {
        return "cmd-wait-rst-" + imei + "-" + serial;
    }

    /**
     * 命令响应
     * @param imei
     * @return
     */
    public static String getLastestTime(String imei) {
        return "device_lastest_time-" + imei;
    }

    /**
     * 设备信息
     * @param imei
     * @return
     */
    public static String getDeviceKeys(String imei) {
        return "device:imei:" + imei;
    }

    /**
     * 设备信息
     * @param plateNo
     * @return
     */
    public static String getDeviceKeysForNoImei(String plateNo) {
        return "device:plate_no:" + plateNo;
    }

    /**
     * 告警
     * @param imei
     * @return
     */
    public static String getAlarmKeys(String imei, String alarmType) {
        return "device_alarm:" + imei + ":_" + alarmType;
    }
}
