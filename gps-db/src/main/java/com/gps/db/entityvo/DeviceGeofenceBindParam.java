package com.gps.db.entityvo;

import java.util.List;
import lombok.Data;

/**
 * @author qulong
 * @date 2020/2/4 17:57
 * @description
 */
@Data
public class DeviceGeofenceBindParam {
    private String geofenceId;
    private List<String> imeis;
}
