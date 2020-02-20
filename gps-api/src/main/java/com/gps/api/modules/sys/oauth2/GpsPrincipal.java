package com.gps.api.modules.sys.oauth2;

import com.gps.db.entity.DeviceEntity;
import com.gps.api.modules.sys.entity.SysUserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GpsPrincipal {
    private SysUserEntity userEntity;
    private DeviceEntity deviceEntity;
    private boolean isPhoneLogin;
    private long tokenId;
}
