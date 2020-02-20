package com.gps.common.g32960.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gps.common.g32960.LoginRequest;
import com.gps.common.g32960.LogoutRequest;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessRegistration {
    String vin;
//    oneof message {
        LoginRequest login;
        LogoutRequest logout;
//    }
}
