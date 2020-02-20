package com.gps.db.service.impl;

import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.db.service.GroupsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gps.db.dao.GroupsDao;
import com.gps.db.entity.GroupsEntity;


@Service("groupsService")
public class GroupsServiceImpl extends ServiceImpl<GroupsDao, GroupsEntity> implements GroupsService {

    @Override
    public MyPage<GroupsEntity> queryPage(Map<String, Object> params) {
        IPage<GroupsEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<GroupsEntity>()
        );

        return MyPage.create(page);
    }

    @Override
    public List<GroupsEntity> listByUser() {
        return baseMapper.listByUser();
    }

}