package com.gps.common.g32960;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//终端校时
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TerminalClockCorrect {
    long systemTime;
}
