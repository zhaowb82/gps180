package com.gps.db.service.impl;

import com.gps.db.dao.DeviceDao;
import com.gps.db.dbutils.MyQuery;
import com.gps.db.dbutils.MyPage;
import com.gps.db.entity.DeviceEntity;
import com.gps.db.service.DeviceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("deviceService")
public class DeviceServiceImpl extends ServiceImpl<DeviceDao, DeviceEntity> implements DeviceService {

    @Override
    public MyPage<DeviceEntity> queryPage(Map<String, Object> params) {
        IPage<DeviceEntity> page = this.page(MyQuery.getPage(params), new QueryWrapper<>());

        return MyPage.create(page);
//        System.out.println("json 正反序列化 begin");
//        String json = JSON.toJSONString(page);
//        Page<User> page1 = JSON.parseObject(json, TypeBuilder.newInstance(Page.class).addTypeParam(User.class).build());
//        print(page1.getRecords());
//        System.out.println("json 正反序列化 end");
    }

    @Override
    public MyPage<DeviceEntity> listPage(Long pageNum, Long pageSize, String orderField, String order, String groupId, String imei) {
        IPage<DeviceEntity> pageO = MyQuery.getPage(pageNum, pageSize, orderField, order);
        IPage<DeviceEntity> page = baseMapper.listPage(pageO, groupId, imei);
        return MyPage.create(page);
    }

    @Override
    public List<DeviceEntity> listByUser() {
        return baseMapper.listByUser();
    }

    @Override
    public MyPage<DeviceEntity> bindGeofenceList(Long pageNum, Long pageSize, String orderField, String order, String companyId, String imei, String geoId) {
        IPage<DeviceEntity> pageO = MyQuery.getPage(pageNum, pageSize, orderField, order);
        return MyPage.create(baseMapper.bindGeofenceDevice(pageO, companyId, imei, geoId));
    }

    @Override
    public MyPage<DeviceEntity> unbindGeofenceList(Long pageNum, Long pageSize, String orderField, String order, String companyId, String imei) {
        IPage<DeviceEntity> pageO = MyQuery.getPage(pageNum, pageSize, orderField, order);
        return MyPage.create(baseMapper.unbindGeofenceDevice(pageO, companyId, imei));
    }

    @Override
    public List<String> listByImei(String imei) {
        return baseMapper.listByImei(imei);
    }

    @Override
    public boolean updatePassword(String imei, String password, String newPassword) {
//        return baseMapper.updatePassword(imei, newPassword);
        DeviceEntity entity = new DeviceEntity();
        entity.setPassword(newPassword);
        return this.update(entity, new QueryWrapper<DeviceEntity>()
                .eq("imei", imei)
                .eq(password != null, "password", password));
    }

}