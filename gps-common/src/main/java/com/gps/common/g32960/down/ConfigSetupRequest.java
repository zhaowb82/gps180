package com.gps.common.g32960.down;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigSetupRequest {
    long setupTime;
    List<Parameter> parameters;

    public int getParametersCount() {
        if (parameters == null) {
            return 0;
        }
        return parameters.size();
    }
}
