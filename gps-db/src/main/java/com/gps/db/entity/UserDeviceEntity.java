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
@TableName("user_device")
public class UserDeviceEntity {

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.INPUT)
	private String id;
	/**
	 * 
	 */
	private Long userId;
	/**
	 * 
	 */
	private String deviceId;

}
