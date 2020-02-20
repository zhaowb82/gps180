package com.gps.api.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@Data
@TableName("sys_dept")
public class SysDeptEntity {
	
	//部门ID
	@TableId
	private Long deptId;
	//上级部门ID，一级部门为0
	private Long parentId;
	//部门名称
	private String name;
	//上级部门名称
	@TableField(exist=false)
	private String parentName;
	//排序
	private Integer orderNum;
	private String type;
	private Long maxGroupNum;
	private Long maxUserNum;
	private Long maxDeviceNum;
	private Boolean enable;
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * ztree属性
	 */
//	@TableField(exist=false)
//	private Boolean open;
//	@TableField(exist=false)
//	private List<?> list;

}
