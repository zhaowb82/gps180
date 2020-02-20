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
 * @date 2019-06-24 20:27:53
 */
@Data
@TableName("command_logs")
public class CommandLogsEntity {

	@TableId(type = IdType.INPUT)
	private String id;
	private String imei;
	private String commandType;
	private String commandBody;
	private String executor;
	private String executorPhone;
	private Date executeTime;
	private Boolean feedbackResult;
	private Date feedbackTime;
	private String platform;
	private String reason;
	private String attribute;

}
