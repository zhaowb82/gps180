package com.gps.db.dao;

import com.gps.db.datascope.PermissionAop;
import com.gps.db.entity.GroupsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
@Mapper
public interface GroupsDao extends BaseMapper<GroupsEntity> {

    @PermissionAop(tableAlias = "g")
    List<GroupsEntity> listByUser();
}
