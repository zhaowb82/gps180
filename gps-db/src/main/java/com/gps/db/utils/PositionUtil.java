package com.gps.db.utils;

import com.gps.db.entity.DeviceStatusEntity;
import org.springframework.beans.BeanUtils;
import com.gps.common.model.Position;

public class PositionUtil {
    public static Position fromDeviceStatus(DeviceStatusEntity status) {
        Position position = new Position();
        if (status != null) {
            try {
                BeanUtils.copyProperties(status, position);

                if (status.getDirection() != null) position.setCourse(status.getDirection());
                position.setImei(status.getImei());
                position.setTime(status.getPositionUpdateTime());
                position.setServerTime(status.getPositionUpdateTime());
                position.setValid(true);
//                position.setAttributes(status.getAttribute());
//                position.setConnectionStatus(status.getConnectionStatus());
//                position.setGeofenceIds(status.getGeofenceIds());
//                position.setAlarmStatus(status.getAlarmStatus());
//                position.setBlockStatus(status.getBlockStatus());
//                position.setGeofenceStatus(status.getGeofenceStatus());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return position;
    }
}
