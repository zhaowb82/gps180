package com.gps.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
@Data
@TableName("alarm")
public class AlarmEntity {

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.INPUT)
	@ApiModelProperty(value = "唯一标识")
	private String id;
//	private String id = String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId());
	private String imei;
	private String type;
	private String remark;
	private Integer level;
	private Date occurTime;
	private Date endTime;
	private Double longitude;
	private Double latitude;
	private Integer occurNum;
	private String companyId;
	/**
	 * 是否处理，1已处理
	 */
	private Integer processStatus;
	private String processUser;
	private String processRemark;

}
