package com.gps.common.g32960.down;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//报警/预警命令
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Warning {
    long requestTime;
    int level;
    String content;
}
