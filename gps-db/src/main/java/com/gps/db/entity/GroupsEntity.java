package com.gps.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
@Data
@TableName("t_groups")
public class GroupsEntity {

	@TableId(value = "id",type = IdType.INPUT)
	private String id;
	private String parentId;
	private Long deptId;
	private String name;
	private Long maxUserNum;
	private Long maxDeviceNum;
	private String remark;
	private Long crtUserId;
	private Date crtTime;

	@TableField(exist=false)
	private List<DeviceEntity> devices;

}
