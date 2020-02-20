package com.gps.db.utils;

import com.gps.db.entity.GeofenceEntity;
import com.gps.common.model.Geofence;

import java.text.ParseException;

public class GeofenceUtil {

    public static Geofence fromEntityGeofence(GeofenceEntity g) {
        Geofence geofence = new Geofence();
        try {
            geofence.setArea(g.getArea());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        geofence.setName(g.getName());
        geofence.setDescription(g.getDescription());
//        geofence.setImei(g.getId());
        return geofence;
    }
}
