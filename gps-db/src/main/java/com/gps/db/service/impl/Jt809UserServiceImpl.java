package com.gps.db.service.impl;

import com.gps.db.dao.Jt809UserDao;
import com.gps.db.entity.Jt809UserEntity;
import com.gps.db.service.Jt809UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("jt809UserService")
public class Jt809UserServiceImpl extends BaseServiceImpl<Jt809UserDao, Jt809UserEntity> implements Jt809UserService {

    public List<Jt809UserEntity> getAllUpUsers() {
        return baseMapper.findAllUpUsers();
    }

}