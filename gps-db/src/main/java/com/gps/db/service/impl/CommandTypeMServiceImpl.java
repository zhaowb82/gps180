package com.gps.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gps.db.dao.CommandTypeMDao;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.CommandTypeMEntity;
import com.gps.db.service.CommandTypeMService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("commandTypeMService")
public class CommandTypeMServiceImpl extends ServiceImpl<CommandTypeMDao, CommandTypeMEntity> implements CommandTypeMService {

    @Override
    public MyPage<CommandTypeMEntity> queryPage(Map<String, Object> params) {
        IPage<CommandTypeMEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<CommandTypeMEntity>()
        );

        return MyPage.create(page);
    }

}