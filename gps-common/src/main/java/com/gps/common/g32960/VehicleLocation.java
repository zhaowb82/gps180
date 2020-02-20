package com.gps.common.g32960;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleLocation {
    boolean isValid;
    double longitude;
    double latitude;
    //    float speed = 4;
    //    int32 direction =5;
}
