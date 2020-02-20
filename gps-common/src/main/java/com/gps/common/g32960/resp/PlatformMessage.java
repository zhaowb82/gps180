package com.gps.common.g32960.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gps.common.g32960.TerminalClockCorrect;
import com.gps.common.g32960.down.ConfigQueryRequest;
import com.gps.common.g32960.down.ConfigSetupRequest;
import com.gps.common.g32960.down.ControlCommand;
import com.gps.common.g32960.down.CustomControlCommand;
import com.gps.common.g32960.down.RemoteUpgradeCommand;
import com.gps.common.g32960.down.Warning;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlatformMessage {
        int messageCase;
//    oneof message {
        ConfigQueryRequest configQuery;
        ConfigSetupRequest configSetup;
        RemoteUpgradeCommand upgrade;
        Warning warning;
        ControlCommand control;
        TerminalClockCorrect clockCorrect;
        EmptyResponse emptyResponse;
        ProtoResponse protoResponse;
        CustomControlCommand customControl;
//    }
}
