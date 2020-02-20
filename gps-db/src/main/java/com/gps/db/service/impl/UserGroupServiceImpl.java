package com.gps.db.service.impl;

import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.db.utils.MapUtils;
import com.gps.db.utils.UIDGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gps.db.dao.UserGroupDao;
import com.gps.db.entity.UserGroupEntity;
import com.gps.db.service.UserGroupService;


@Service("userGroupService")
public class UserGroupServiceImpl extends ServiceImpl<UserGroupDao, UserGroupEntity> implements UserGroupService {

    @Override
    public MyPage<UserGroupEntity> queryPage(Map<String, Object> params) {
        IPage<UserGroupEntity> page = this.page(
                MyQuery.getPage(params),
                new QueryWrapper<UserGroupEntity>()
        );

        return MyPage.create(page);
    }

    @Override
    public void saveOrUpdate(Long userId, List<String> gourpIdList) {
        //先删除用户与角色关系
        this.removeByMap(new MapUtils().put("user_id", userId));

        if (gourpIdList == null || gourpIdList.size() == 0) {
            return;
        }

        //保存用户与角色关系
        for (String groupId : gourpIdList) {
            UserGroupEntity sysUserRoleEntity = new UserGroupEntity();
            sysUserRoleEntity.setId(UIDGenerator.eventId());
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setGroupId(groupId);
            this.save(sysUserRoleEntity);
        }
    }

    @Override
    public List<String> queryGroupIdList(long userId) {
        return baseMapper.queryGroupIdList(userId);
    }

}