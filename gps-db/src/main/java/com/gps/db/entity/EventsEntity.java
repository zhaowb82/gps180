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
@TableName("events")
public class EventsEntity {

	@TableId(type = IdType.INPUT)
	private String id;
	private String imei;
	private String type;
	private String level;
	private Date occurTime;
	private Double longitude;
	private Double latitude;
	private String geofenceId;
	private String companyId;
	@TableField(typeHandler = MapDateTypeHandler.class)
	private Map<String, Object> attributes;

}
