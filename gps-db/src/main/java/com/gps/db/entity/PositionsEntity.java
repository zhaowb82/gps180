package com.gps.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.Map;

import com.gps.db.db.MapDateTypeHandler;
import lombok.Data;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
@Data
@TableName("positions")
public class PositionsEntity {

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.INPUT)
	private String id;
	/**
	 * 
	 */
	private String imei;
	/**
	 * 
	 */
	private Date deviceTime;
	/**
	 * 
	 */
	private Double latitude;
	/**
	 * 
	 */
	private Double longitude;
	/**
	 * 
	 */
	private Float speed;
	/**
	 * 
	 */
	private Float direction;
	private Double totalDistance;
	private String gotsrc;
	/**
	 * 
	 */
	@TableField(typeHandler = MapDateTypeHandler.class)
	private Map<String, Object> attribute;
	/**
	 * 
	 */
	private String orgMsg;

}
