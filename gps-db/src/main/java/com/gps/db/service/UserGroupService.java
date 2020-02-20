package com.gps.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.UserGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-30 11:13:45
 */
public interface UserGroupService extends IService<UserGroupEntity> {

    MyPage<UserGroupEntity> queryPage(Map<String, Object> params);

    void saveOrUpdate(Long userId, List<String> gourpIdList);

    List<String> queryGroupIdList(long userId);
}

