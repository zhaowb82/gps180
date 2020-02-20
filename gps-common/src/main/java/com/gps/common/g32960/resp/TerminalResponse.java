package com.gps.common.g32960.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gps.common.g32960.down.ConfigQueryResponse;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TerminalResponse {
    String vin;
//    oneof message {
        ConfigQueryResponse config_query;
        boolean setup_success;
        boolean control_success;
//    }
}
