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
 * @date 2019-06-26 09:26:45
 */
@Data
@TableName("jt809_user")
public class Jt809UserEntity {

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.INPUT)
	private String id;
	/**
	 * 平台ID
	 */
	private Long gnssCenterId;
	/**
	 * 用户类型：DOWN_USER(下级平台), UP_USER(上级平台)
	 */
	private String userType;
	/**
	 * 上级平台IP地址
	 */
	private String platformIp;
	/**
	 * 上级平台端口
	 */
	private Integer platformPort;
	/**
	 * 用户名
	 */
	private Long userId;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 是否加密
	 */
	private Integer enrypt;
	/**
	 * 秘钥
	 */
	private Integer encryptKey;
	/**
	 * M1
	 */
	private Long miyaoM;
	/**
	 * A1
	 */
	private Long miyaoA;
	/**
	 * C1
	 */
	private Long miyaoC;
	/**
	 * 子链路连接
	 */
	private Boolean connSubLink;
	/**
	 * 备注
	 */
	private String remark;

}
