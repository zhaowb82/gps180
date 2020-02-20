package com.gps.api.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gps.api.modules.sys.entity.SysDeptEntity;
import com.gps.db.datascope.PermissionAop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@Mapper
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

    @PermissionAop(tableAlias = "d")
    List<SysDeptEntity> listByUser();
}
