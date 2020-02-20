package com.gps.common.g32960.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gps.common.g32960.LoginRequest;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProtoResponse {
    int messsageType;
    int result;
//    oneof proto {
        LoginRequest login;
//    }
}
