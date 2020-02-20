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
@TableName("geofence")
public class GeofenceEntity {

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.INPUT)
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 区域
	 */
	private String area;
	/**
	 * 附加参数
	 */
	@TableField(typeHandler = MapDateTypeHandler.class)
	private Map<String, Object> attributes;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 所属公司
	 */
	private String companyId;
	/**
	 * 围栏图标
	 */
	private String icon;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
