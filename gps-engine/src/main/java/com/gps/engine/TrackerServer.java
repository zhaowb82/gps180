
package com.gps.engine;

import com.gps.common.model.BaseModel;
import com.gps.common.model.Device;
import com.gps.common.model.Position;
import com.gps.db.entity.DeviceEntity;
import com.gps.db.entity.DeviceStatusEntity;
import com.gps.db.entity.PositionsEntity;
import com.gps.db.service.DeviceService;
import com.gps.db.service.DeviceStatusService;
import com.gps.db.service.PositionsService;
import com.gps.db.utils.UIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TrackerServer {
    @Autowired
    private DeviceService deviceBiz;
    @Autowired
    private DeviceStatusService deviceStatusBiz;
    @Autowired
    private PositionsService positionsService;

    public void postData(BaseModel baseModel) {
        if (StringUtils.isBlank(baseModel.getImei())) {
            return;
        }
        processBaseModel(baseModel);
    }

    private String imei;
    public void checkImei(BaseModel baseModel) { //809 imei号处理
        DeviceEntity d = deviceBiz.getById(baseModel.getImei());
        if (d == null) {
            d = new DeviceEntity();
            d.setImei(baseModel.getImei());
            d.setPlateNo(baseModel.getImei());
            d.setDeviceType(0);
            d.setTotalUp(0);
            d.setCompanyId("1");
            d.setCrtUserId("1");
            d.setCrtTime(new Date());
            d.setProtocol(baseModel.getProtocol());
            deviceBiz.save(d);
        }
        imei = baseModel.getImei();
        DeviceStatusEntity deviceStatusEntity = getDeviceStatusEntity();
        if (deviceStatusEntity == null) {
            DeviceStatusEntity deviceStatus = new DeviceStatusEntity();
            deviceStatus.setImei(imei);
            deviceStatus.setConnectionStatus(Device.STATUS_OFFLINE);
            deviceStatus.setLatitude(0.0);
            deviceStatus.setLongitude(0.0);
            deviceStatus.setSpeed(0f);
            deviceStatus.setDirection(0f);
            deviceStatus.setGeofenceStatus(0);
            deviceStatusBiz.save(deviceStatus);
            deviceStatusBiz.updateDeviceWithCache(deviceStatus, false, true);
        }
    }

    public DeviceStatusEntity getDeviceStatusEntity() {
        return deviceStatusBiz.getDeviceWithCache(imei);
    }
    private void processBaseModel(BaseModel baseModel) {
        try {
            String imei = baseModel.getImei();
            if (StringUtils.isEmpty(imei)) {
                return;
            }
            checkImei(baseModel);
            if (baseModel.getMessageType() == null) {
                log.error("baseModel.getMessageType() == null");
                return;
            }
            //start process
            switch (baseModel.getMessageType()) {
                case TYPE_POSITIONS:
                    processPostion(baseModel);
                    break;
                case TYPE_RECORD:
                    break;
                case TYPE_EVENTS:
                    break;
            }
        } catch (Exception e) {
            log.error("Error process", e);
        } finally {
            deviceStatusBiz.updateDeviceWithCache(getDeviceStatusEntity(),true, false);
            imei = null;
        }
    }

    private void processPostion(BaseModel baseModel) {
        if (baseModel.getPositions() != null) {
            for (Position o : baseModel.getPositions()) {
                if (o == null) {
                    continue;
                }
                o.setImei(baseModel.getImei());
                processPostion(o);
            }
        } else if (baseModel.getPosition() != null) {
            baseModel.getPosition().setImei(baseModel.getImei());
            processPostion(baseModel.getPosition());
        }
    }

    private void processPostion(Position position) {
        if (position.getLongitude() == null || position.getLatitude() == null) {
            return;
        }
        if (position.isValidPos()) {
            position.setGotsrc("gps");
        }
        DeviceStatusEntity deviceStatus = getDeviceStatusEntity();
        double lstLat = deviceStatus.getLatitude();
        double lstLng = deviceStatus.getLongitude();
        // 写状态数据
        copyData(deviceStatus, position);
        //更新缓存数据
        deviceStatusBiz.updateDeviceWithCache(deviceStatus, false, true);
        if (position.isValidPos() && position.getLatitude() != lstLat && position.getLongitude() != lstLng) {
            PositionsEntity ps = new PositionsEntity();
            BeanUtils.copyProperties(position, ps);
            BeanUtils.copyProperties(deviceStatus, ps);
            ps.setId(UIDGenerator.eventId());
            positionsService.save(ps);
        }
    }
    private void copyData(DeviceStatusEntity deviceStatus, Position position) {
        if (position.getGotsrc() != null) {
            deviceStatus.setGotsrc(position.getGotsrc());
        }
        boolean posValid = false;
        if (position.isValidPos()) {
            deviceStatus.setLatitude(position.getLatitude());
            deviceStatus.setLongitude(position.getLongitude());
            deviceStatus.setSpeed(position.getSpeed());
            deviceStatus.setDirection((float) position.getCourse());
            posValid = true;
        }
        Map<String, Object> att = position.getAttributes();
        if (att != null) {
            if (att.get(Position.KEY_BLOCKED) != null) {
                deviceStatus.setBlockStatus(position.getBoolean(Position.KEY_BLOCKED) ? 2 : 1);
            }
            for (Map.Entry<String, Object> mp : att.entrySet()) {
                Map<String, Object> srcAtt = deviceStatus.getAttribute();
                if (srcAtt == null) {
                    srcAtt = new HashMap<>();
                    deviceStatus.setAttribute(srcAtt);
                }
                srcAtt.put(mp.getKey(), mp.getValue());
            }
        }
        if (posValid) {
            if (null != position.getFixTime()) {
                deviceStatus.setPositionUpdateTime(position.getFixTime());
            } else {
                deviceStatus.setPositionUpdateTime(new Date());
            }
        }
    }

}
