package com.gps.api.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色与部门对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年6月21日 23:28:13
 */
@Data
@TableName("sys_role_dept")
public class SysRoleDeptEntity {

	@TableId
	private Long id;
	private Long roleId;
	private Long deptId;

}
