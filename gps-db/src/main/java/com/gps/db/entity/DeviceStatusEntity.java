package com.gps.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gps.db.db.ListDateTypeHandler;
import com.gps.db.db.MapDateTypeHandler;
import lombok.Data;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 17:23:20
 */
@Data
@TableName("device_status")
public class DeviceStatusEntity {

	@TableId(type = IdType.INPUT)
	private String imei;
	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 纬度
	 */
	private Double latitude;
	private Float speed;
	private Float direction;
	private Double totalDistance;
	private String gotsrc;
	/**
	 * 连接状态
	 */
	private String connectionStatus;
	/**
	 * 最后位置更新时间
	 */
	private Date positionUpdateTime;
	/**
	 * 最后信号更新时间
	 */
	private Date signalUpdateTime;
	/**
	 * 在围栏内的围栏ID
	 */
	@TableField(typeHandler = ListDateTypeHandler.class)
	private List<String> geofenceIds;
	/**
	 * 围栏状态（围栏外：-1 /围栏内：1）
	 */
	private Integer geofenceStatus;
	/**
	 * 设防状态（1：未设防， 2： 设防）
	 */
	private Integer blockStatus;
	/**
	 * 报警状态（0: 无告警，1：拆除报警, 2：）
	 */
	private Integer alarmStatus;
	/**
	 * 所有报警
	 */
	@TableField(typeHandler = ListDateTypeHandler.class)
	private List<String> alarms;
	/**
	 * 上报频率，单位分钟
	 */
	private Integer reportFrequency;
	/**
	 * 扩展属性
	 */
	@TableField(typeHandler = MapDateTypeHandler.class)
	private Map<String, Object> attribute;
	/**
	 * 原始消息报文
	 */
	private String orgMsg;

}
