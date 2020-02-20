
package com.gps.common.geofence;

import java.io.Serializable;
import java.text.ParseException;

public abstract class GeofenceGeometry implements Serializable {

    public abstract boolean containsPoint(double latitude, double longitude);

    public abstract String toWkt();

    public abstract void fromWkt(String wkt) throws ParseException;

    public static class Coordinate implements Serializable {

        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }

}
