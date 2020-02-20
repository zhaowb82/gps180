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
@TableName("command")
public class CommandEntity {

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.INPUT)
	private String id;
	/**
	 * 命令名称
	 */
	private String name;
	/**
	 * 命令编码
	 */
	private String code;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 离线有效时间，单位秒
	 */
	private Integer expire;

}
