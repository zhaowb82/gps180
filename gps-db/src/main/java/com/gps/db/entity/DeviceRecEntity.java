package com.gps.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.gps.db.db.BlobDataTypeHandler;
import com.gps.db.db.MapDateTypeHandler;
import lombok.Data;

import java.sql.Blob;
import java.util.Date;

import static org.apache.ibatis.type.JdbcType.BLOB;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2020-01-22 16:33:18
 */
@Data
@TableName("device_rec")
public class DeviceRecEntity {

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.INPUT)
	private String id;
	/**
	 * 
	 */
	private String imei;
	/**
	 * 录音时间
	 */
	private Date recTime;
	/**
	 * 录音时间
	 */
	private Integer fileSize;
	/**
	 * 录音内容
	 */
	@TableField(typeHandler = BlobDataTypeHandler.class)
	private byte[] recData;

}
