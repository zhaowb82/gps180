package com.gps.common.g32960.down;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomControlCommand {
    long requestTime;
    int commandValue;
}
