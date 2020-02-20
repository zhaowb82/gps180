package com.gps.common.model;

import java.util.Date;
import java.util.List;

public class Device extends ExtendedModel {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    private String uniqueId;
//
//    public String getUniqueId() {
//        return uniqueId;
//    }
//
//    public void setUniqueId(String uniqueId) {
//        this.uniqueId = uniqueId;
//    }

    public static final String STATUS_UNKNOWN = "unknown";
    public static final String STATUS_ONLINE = "online";
    public static final String STATUS_OFFLINE = "offline";

    private String status;

    public String getStatus() {
        return status != null ? status : STATUS_OFFLINE;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private Date lastUpdate;

    public Date getLastUpdate() {
        if (lastUpdate != null) {
            return new Date(lastUpdate.getTime());
        } else {
            return null;
        }
    }

    public void setLastUpdate(Date lastUpdate) {
        if (lastUpdate != null) {
            this.lastUpdate = new Date(lastUpdate.getTime());
        } else {
            this.lastUpdate = null;
        }
    }

//    private long positionId;
//
//    @QueryIgnore
//    public long getPositionId() {
//        return positionId;
//    }
//
//    public void setPositionId(long positionId) {
//        this.positionId = positionId;
//    }

    private List<String> geofenceIds;

    public List<String> getGeofenceIds() {
        return geofenceIds;
    }

    public void setGeofenceIds(List<String> geofenceIds) {
        this.geofenceIds = geofenceIds;
    }

//    private String phone;
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }

//    private String model;
//
//    public String getModel() {
//        return model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }

//    private String contact;
//
//    public String getContact() {
//        return contact;
//    }
//
//    public void setContact(String contact) {
//        this.contact = contact;
//    }

//    private String category;
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }

//    private boolean disabled;
//
//    public boolean getDisabled() {
//        return disabled;
//    }
//
//    public void setDisabled(boolean disabled) {
//        this.disabled = disabled;
//    }

}
