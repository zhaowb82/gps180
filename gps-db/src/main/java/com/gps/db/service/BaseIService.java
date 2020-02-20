package com.gps.db.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.CommandTypeEntity;


/**
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-30 11:13:45
 */
public interface BaseIService<T> extends IService<T> {

    MyPage<T> queryPage(Long pageNum, Long pageSize, String sort, String order,
                        Wrapper<T> queryWrapper);

}

