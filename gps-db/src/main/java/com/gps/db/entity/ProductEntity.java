package com.gps.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
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
@TableName("product")
public class ProductEntity {

	@TableId(value = "id",type = IdType.INPUT)
	private String id;
	private String manufacturer;
	private String deviceModel;
	private String protocol;
	private String deviceType;
	private String commandType;
	@TableField(typeHandler = ListDateTypeHandler.class)
	private List<String> functions;
	private String deviceDesc;
	private String reg;

}
