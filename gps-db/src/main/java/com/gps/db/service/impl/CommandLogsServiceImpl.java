package com.gps.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gps.db.dao.CommandLogsDao;
import com.gps.db.dbutils.MyPage;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.entity.CommandLogsEntity;
import com.gps.db.service.CommandLogsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service("commandLogsService")
public class CommandLogsServiceImpl extends ServiceImpl<CommandLogsDao, CommandLogsEntity> implements CommandLogsService {

    @Override
    public MyPage<CommandLogsEntity> queryPage(Map<String, Object> params) {
        IPage<CommandLogsEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<CommandLogsEntity>()
        );

        return MyPage.create(page);
    }


    @Override
    public MyPage<CommandLogsEntity> queryPage(Long pageNum, Long pageSize, String sort, String order,
                                               String imei, Date startDate, Date endDate) {
        IPage<CommandLogsEntity> pageO = MyQuery.getPage(pageNum, pageSize, sort, order);
        IPage<CommandLogsEntity> page = baseMapper.commandList(pageO, imei, startDate, endDate);
        return MyPage.create(page);
    }
}