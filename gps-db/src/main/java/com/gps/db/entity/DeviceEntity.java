package com.gps.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @date 2019-06-24 17:23:20
 */
@Data
@TableName("device")
public class DeviceEntity {

	@TableId(value = "imei",type = IdType.INPUT)
	private String imei;
	private String sim;
	private String iccid;
	private Date simStartDate;
	private Date simEndDate;
	private String protocol;
	private String productId;
	private String plateNo;
	/**
	 * 设备类型(0: 有线， 1：无线)
	 */
	private Integer deviceType;
	private String carVin;
	private String driverName;
	private String companyId;
	private String groupId;
	private Boolean canLogin;
	private String password;
	private String remark;
	/**
	 * 设备状态：1：正常绑定，0：空闲2：待审核
	 */
	private Integer status;
	private Integer totalUp;
	private Boolean isTest;
	@TableField(fill = FieldFill.INSERT)
	private String crtUserId;
	@TableField(fill = FieldFill.INSERT)
	private Date crtTime;
	@TableField(fill = FieldFill.UPDATE)
	private String updateUserId;
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

}
