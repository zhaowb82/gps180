package com.gps.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-30 11:13:45
 */
@Data
@TableName("command_type")
public class CommandTypeEntity {

	@TableId(value = "predict_cmd_id", type = IdType.INPUT)
	private Long predictCmdId;
	private String deviceType;
	private String cmdCode;
	private String cmdName;
	private String cmdNameEn;
	private String cmdNameTw;
	private String cmdDescr;
	private String cmdDescrEn;
	private String cmdDescrTw;
	private String params;
	private String paramsEn;
	private String paramsTw;
	private String cmdType;

	private String cmdPwd;
	private Integer cmdLevel;
	private Boolean sync;

}
