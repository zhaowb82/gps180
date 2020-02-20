package com.gps.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-10-26 09:26:45
 */
@Data
@TableName("jt809_dev_plat")
public class Jt809DevPlatEntity {

	@TableId(value = "id",type = IdType.INPUT)
	private Long id;
	private String imei;
	private String gnss;

}
