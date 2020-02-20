package com.gps.db.service.impl;

import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import java.util.List;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gps.db.dao.CommandTypeDao;
import com.gps.db.entity.CommandTypeEntity;
import com.gps.db.service.CommandTypeService;


@Service("commandTypeService")
public class CommandTypeServiceImpl extends ServiceImpl<CommandTypeDao, CommandTypeEntity> implements CommandTypeService {

    @Override
    public MyPage<CommandTypeEntity> queryPage(Map<String, Object> params) {
        IPage<CommandTypeEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<CommandTypeEntity>()
        );

        return MyPage.create(page);
    }

    @Override
    public List<CommandTypeEntity> getByImei(String imei) {
        return baseMapper.cmdListByImei(imei);
    }

}