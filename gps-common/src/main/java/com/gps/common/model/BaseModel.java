
package com.gps.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.gps.common.MessageType;

/**
 * 推送数据
 */
@Data
@NoArgsConstructor
public class BaseModel implements Serializable {

    private String imei;
    private MessageType messageType;
    private String protocol;
    private Date serverTime;
    private Position position;
    private List<Position> positions;
    private CommandResult commandResult;
    private RecordData recordData;
    private Event event;
    private String orgHexString;
    private List<String> gnss;

    public BaseModel(String imei) {
        this.imei = imei;
        this.serverTime = new Date();
    }

}
