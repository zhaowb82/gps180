package com.gps.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 命令响应数据实体类
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CommandResult extends Message {

    //gate端用作命令流水号，传递后用作命令唯一编码（MySQL对应）
    private int commandFlowId;
    private int orgCommandType;//原命令ID 0x8001
    private boolean result;//执行结果
    private String reason;//error reason
    private Object resultObject;//命令返回结构体

    private String commandLogId; //对应数据库的ID字段
    private String userId;  //用户ID
    private String userPhone;//命令发起人电话
    private String platform;//命令发起平台

}
