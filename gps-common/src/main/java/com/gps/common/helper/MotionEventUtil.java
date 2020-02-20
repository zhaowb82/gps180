package com.gps.common.helper;

import com.gps.common.model.Position;
import com.gps.common.reports.ReportUtils;
import com.gps.common.reports.model.TripsConfig;
import com.gps.common.model.DeviceState;
import com.gps.common.model.Event;

import java.util.Collections;
import java.util.Map;

public class MotionEventUtil {

    private static Map<Event, Position> newEvent(String imei, DeviceState deviceState, boolean newMotion) {
        String eventType = newMotion ? Event.TYPE_DEVICE_MOVING : Event.TYPE_DEVICE_STOPPED;
        Position position = deviceState.getMotionPosition();
        Event event = new Event(eventType, imei);
        deviceState.setMotionState(newMotion);
        deviceState.setMotionPosition(null);
        return Collections.singletonMap(event, position);
    }

    public static Map<Event, Position> updateMotionState(String imei, TripsConfig tripsConfig, DeviceState deviceState) {
        Map<Event, Position> result = null;
        if (deviceState.getMotionState() != null && deviceState.getMotionPosition() != null) {
            boolean newMotion = !deviceState.getMotionState();
            Position motionPosition = deviceState.getMotionPosition();
            long currentTime = System.currentTimeMillis();
            long motionTime = motionPosition.getFixTime().getTime()
                    + (newMotion ? tripsConfig.getMinimalTripDuration() : tripsConfig.getMinimalParkingDuration());
            if (motionTime <= currentTime) {
                result = newEvent(imei, deviceState, newMotion);
            }
        }
        return result;
    }

    public static Map<Event, Position> updateMotionState(String imei, TripsConfig tripsConfig, DeviceState deviceState, Position position) {
        return updateMotionState(imei, tripsConfig, deviceState, position, position.getBoolean(Position.KEY_MOTION));
    }

    public static Map<Event, Position> updateMotionState(String imei, TripsConfig tripsConfig, DeviceState deviceState, Position position, boolean newMotion) {
        Map<Event, Position> result = null;
        Boolean oldMotion = deviceState.getMotionState();

        long currentTime = position.getFixTime().getTime();
        if (newMotion != oldMotion) {
            if (deviceState.getMotionPosition() == null) {
                deviceState.setMotionPosition(position);
            }
        } else {
            deviceState.setMotionPosition(null);
        }

        Position motionPosition = deviceState.getMotionPosition();
        if (motionPosition != null) {
            long motionTime = motionPosition.getFixTime().getTime();
            double distance = ReportUtils.calculateDistance(motionPosition, position, false);
            Boolean ignition = null;
            if (tripsConfig.isUseIgnition() && position.getAttributes().containsKey(Position.KEY_IGNITION)) {
                ignition = position.getBoolean(Position.KEY_IGNITION);
            }
            if (newMotion) {// 静 -> 动
                if (currentTime - motionTime >= tripsConfig.getMinimalTripDuration()
                        || distance >= tripsConfig.getMinimalTripDistance()) {
                    result = newEvent(imei, deviceState, newMotion);
                }
            } else {// 动 -> 静
                if (currentTime - motionTime >= tripsConfig.getMinimalParkingDuration()
                        || (ignition != null && !ignition)) {
                    result = newEvent(imei, deviceState, newMotion);
                }
            }
        }
        return result;
    }
}
