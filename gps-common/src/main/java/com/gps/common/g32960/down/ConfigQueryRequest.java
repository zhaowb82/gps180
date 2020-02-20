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
public class ConfigQueryRequest {
    long queryTime;
    List<Integer> parameterIds;

    public int getParameterIdsCount() {
        if (parameterIds == null) {
            return 0;
        }
        return parameterIds.size();
    }
}
