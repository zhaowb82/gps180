package com.gps.db.service;

import com.gps.db.entity.Jt809UserEntity;

import java.util.List;

/**
 * @author zb
 * @email zb@gmail.com
 * @date 2019-06-26 09:26:45
 */
public interface Jt809UserService extends BaseIService<Jt809UserEntity> {

    List<Jt809UserEntity> getAllUpUsers();
}

