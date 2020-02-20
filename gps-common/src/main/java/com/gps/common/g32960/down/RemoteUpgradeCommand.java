package com.gps.common.g32960.down;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//远程升级命令
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemoteUpgradeCommand {
    long requestTime;
    String dialName;//拨号点名称
    String dialAccount;//拨号用户名
    String dialPassword;//拨号密码
    String address;//地址
    int port;//
    String terminalManufacturerId;//车载终端制造商ID
    String hardwareVersion;
    String firmwareVersion;//固件版本
    String upgradeUrl;
    int upgradeTimeLimit;//连接到升级服务器时限
}
