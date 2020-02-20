package com.gps.common.model;

import java.text.ParseException;

import com.gps.common.geofence.GeofenceCircle;
import com.gps.common.geofence.GeofenceGeometry;
import com.gps.common.geofence.GeofencePolygon;
import com.gps.common.geofence.GeofencePolyline;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Geofence extends ScheduledModel {

    public static final String TYPE_GEOFENCE_CILCLE = "geofenceCircle";
    public static final String TYPE_GEOFENCE_POLYGON = "geofencePolygon";
    public static final String TYPE_GEOFENCE_POLYLINE = "geofencePolyline";

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) throws ParseException {
        if (area.startsWith("CIRCLE")) {
            geometry = new GeofenceCircle(area);
        } else if (area.startsWith("POLYGON")) {
            geometry = new GeofencePolygon(area);
        } else if (area.startsWith("LINESTRING")) {
            final double distance = getDouble("polylineDistance");
//            geometry = new GeofencePolyline(area, distance > 0 ? distance : Context.getConfig().getDouble("geofence.polylineDistance", 25));
            geometry = new GeofencePolyline(area, distance > 0 ? distance : 25);
        } else {
            throw new ParseException("Unknown geometry type", 0);
        }

        this.area = area;
    }

    private GeofenceGeometry geometry;

    @JsonIgnore
    public GeofenceGeometry getGeometry() {
        return geometry;
    }

    @JsonIgnore
    public void setGeometry(GeofenceGeometry geometry) {
        area = geometry.toWkt();
        this.geometry = geometry;
    }
}
