package com.gps.db.service.impl;

import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.db.service.CommandService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gps.db.dao.CommandDao;
import com.gps.db.entity.CommandEntity;


@Service("commandService")
public class CommandServiceImpl extends ServiceImpl<CommandDao, CommandEntity> implements CommandService {

    @Override
    public MyPage<CommandEntity> queryPage(Map<String, Object> params) {
        IPage<CommandEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<CommandEntity>()
        );

        return MyPage.create(page);
    }

}