package com.gps.common.reports.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeviceReport {

    private String deviceName;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    private String groupName = "";

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private List<?> objects;

    public Collection<?> getObjects() {
        return objects;
    }

    public void setObjects(Collection<?> objects) {
        this.objects = new ArrayList<>(objects);
    }

}
