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
@TableName("device_geofence")
public class DeviceGeofenceEntity {

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.INPUT)
	private String id;
	/**
	 * 设备imei
	 */
	private String imei;
	/**
	 * 围栏id
	 */
	private String geofenceId;

}
