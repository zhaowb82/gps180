package com.gps.common.g32960.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmptyResponse {
    int messsageType;
    int result;
    boolean hasTime;
    long time;
}
