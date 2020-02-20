package com.gps.common.helper;

import com.gps.common.model.Position;
import lombok.Data;
import com.gps.common.model.DeviceState;
import com.gps.common.model.Event;

import java.util.Collections;
import java.util.Map;

public class OverspeedEventUtil {

    public static final String ATTRIBUTE_SPEED_LIMIT = "speedLimit";

    @Data
    public static class OverspeedConfig {
        private boolean notRepeat;
        private boolean preferLowest;
        private long minimalDuration;
        public OverspeedConfig(long minimalDuration, boolean notRepeat, boolean preferLowest) {
            this.notRepeat = notRepeat;
            this.minimalDuration = minimalDuration;
            this.preferLowest = preferLowest;
        }
    }

    private static Map<Event, Position> newEvent(OverspeedConfig config, DeviceState deviceState, double speedLimit) {
        Position position = deviceState.getOverspeedPosition();
        Event event = new Event(Event.TYPE_DEVICE_OVERSPEED, position.getImei()/*, position.getId()*/);
        event.set("speed", deviceState.getOverspeedPosition().getSpeed());
        event.set(ATTRIBUTE_SPEED_LIMIT, speedLimit);
        event.setGeofenceId(deviceState.getOverspeedGeofenceId());
        deviceState.setOverspeedState(config.notRepeat);
        deviceState.setOverspeedPosition(null);
        deviceState.setOverspeedGeofenceId(null);
        return Collections.singletonMap(event, position);
    }

    public static Map<Event, Position> updateOverspeedState(OverspeedConfig config, DeviceState deviceState, double speedLimit) {
        Map<Event, Position> result = null;
        if (deviceState.getOverspeedState() != null && !deviceState.getOverspeedState()
                && deviceState.getOverspeedPosition() != null && speedLimit != 0) {
            long currentTime = System.currentTimeMillis();
            Position overspeedPosition = deviceState.getOverspeedPosition();
            long overspeedTime = overspeedPosition.getFixTime().getTime();
            if (overspeedTime + config.minimalDuration <= currentTime) {
                result = newEvent(config, deviceState, speedLimit);
            }
        }
        return result;
    }

    public static Map<Event, Position> updateOverspeedState(OverspeedConfig config,
                                                            DeviceState deviceState, Position position,
                                                            double speedLimit, String geofenceId) {
        Map<Event, Position> result = null;

        Boolean oldOverspeed = deviceState.getOverspeedState();

        long currentTime = position.getFixTime().getTime();
        boolean newOverspeed = position.getSpeed() > speedLimit;
        if (newOverspeed && !oldOverspeed) {
            if (deviceState.getOverspeedPosition() == null) {
                deviceState.setOverspeedPosition(position);
                deviceState.setOverspeedGeofenceId(geofenceId);
            }
        } else if (oldOverspeed && !newOverspeed) {
            deviceState.setOverspeedState(false);
            deviceState.setOverspeedPosition(null);
            deviceState.setOverspeedGeofenceId(null);
        } else {
            deviceState.setOverspeedPosition(null);
            deviceState.setOverspeedGeofenceId(null);
        }
        Position overspeedPosition = deviceState.getOverspeedPosition();
        if (overspeedPosition != null) {
            long overspeedTime = overspeedPosition.getFixTime().getTime();
            if (newOverspeed && overspeedTime + config.minimalDuration <= currentTime) {
                result = newEvent(config, deviceState, speedLimit);
            }
        }
        return result;
    }
}
