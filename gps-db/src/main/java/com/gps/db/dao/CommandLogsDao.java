package com.gps.db.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gps.db.datascope.PermissionAop;
import com.gps.db.entity.CommandLogsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-24 20:27:53
 */
@Mapper
public interface CommandLogsDao extends BaseMapper<CommandLogsEntity> {

    @PermissionAop(tableAlias = "d")
    IPage<CommandLogsEntity> commandList(@Param("page") IPage<CommandLogsEntity> page, @Param("imei") String imei,
                                          @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
