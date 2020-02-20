package com.gps.api.ws;

import com.alibaba.fastjson.JSON;
import com.gps.api.modules.sys.entity.SysDeptEntity;
import com.gps.db.entity.DeviceEntity;
import com.gps.db.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gps.common.MessageType;
import com.gps.common.model.BaseModel;
import com.gps.common.model.CommandResult;
import com.gps.common.model.Event;
import com.gps.common.model.Position;
import com.gps.websocket.WebSocket;
import com.gps.websocket.WebSocketManager;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * socket 数据推送处理
 */
@Slf4j
@Component
public class WebSocketPush {
    @Autowired
    private DeviceService deviceBiz;

    @Autowired
    private WebSocketManager webSocketManager;

    //concurrent包的线程安全Set，用来存放每个客户端(Mac)对应的WebSocket对象。
    private Map<String, Set<String>> userSet = new ConcurrentHashMap<>(500);
    private Map<String, Set<String>> deptSet = new ConcurrentHashMap<>(500);
    private Map<String, Set<String>> deviceSet = new ConcurrentHashMap<>(500);
    private Map<String, String> sessionIdAndUDD = new ConcurrentHashMap<>(500);

//    @Autowired
//    private NotificationSettingssBiz notificationSettingsBiz;

//    private void notifyPosition(BaseModel baseModel, Device device, String rentId, Position position) throws ParseException, IOException {
//        position.set("deviceId", device.getId());
//        position.set("plateNo", device.getPlateNo());
//        //围栏内外
//        Geofence geofence = geofenceBiz.findGeofenceByDeviceId(device.getId());
//
//        if (geofence == null) { //设备未绑定围栏
//            position.set("geofenceStatus", 0);
//        } else {
////            int geofenceStatu = GeofenceUtil.checkGeofence(geofence.getArea(), position.getLatitude(), position.getLongitude());
////            position.set("geofenceStatus", geofenceStatu);
//        }
//        baseModel.setEvent(null);
//        baseModel.setPosition(position);
//        baseModel.setAdditionalAttributes(null);
//        baseModel.setCommandResult(null);
//        this.pushMessageToDept(rentId, JSON.toJSONString(baseModel));
//    }


    /**
     * 往指定公司下的所有在线用户推送数据
     */
    private void pushMessageToDept(String deptId, String message) {
        //获取到该公司下目前登录的用户的socket
        Set<String> depS = deptSet.get(deptId);
        if (depS != null) {
            for (String sesId : depS) {
                sendMsg(sesId, message);
            }
        }
//        webSocketManager.broadcast(message);
    }

    private void pushMessageToUser(String userId, String message) {
        Set<String> uS = userSet.get(userId);
        if (uS != null) {
            for (String sesId : uS) {
                sendMsg(sesId, message);
            }
        }
    }

    private void pushMessageToDevice(String imei, String message) {
        Set<String> devS = deviceSet.get(imei);
        if (devS != null) {
            for (String sesId : devS) {
                sendMsg(sesId, message);
            }
        }
    }

    private void sendMsg(String sesId, String message) {
        try {
            webSocketManager.sendMessage(sesId, message);
        } catch (Exception e) {
//            e.printStackTrace();
            log.info("{} not online", sesId);
        }
    }

    public void notifyPos(Position position) {
        String imei = position.getImei(); //获取推送数据的imei号
        String deptId = position.getString("deptId");
        if (StringUtils.isEmpty(deptId)) {
            DeviceEntity device = deviceBiz.getById(imei);
            if (device != null) {
                deptId = device.getCompanyId(); //获取上报设备的所属公司id，用于数据推送
            }
        }
//        //围栏内外
//        Geofence geofence = geofenceBiz.findGeofenceByDeviceImei(device.getImei());
//        if (geofence == null) { //设备未绑定围栏
//            position.set("geofenceStatus", 0);
//        } else {
//            int geofenceStatu = GeofenceUtil.checkGeofence(geofence.getArea(), position.getLatitude(), position.getLongitude());
//            position.set("geofenceStatus", geofenceStatu);
//        }
        BaseModel baseModel = new BaseModel(imei);
        baseModel.setMessageType(MessageType.TYPE_POSITIONS);
        baseModel.setPosition(position);
        String msg = JSON.toJSONString(baseModel);
        if (StringUtils.isNotEmpty(deptId)) {
            pushMessageToDept(deptId, msg);
        }
        pushMessageToDevice(imei, msg);
    }

    public void notifyEvt(Event event) {
        String imei = event.getImei(); //获取推送数据的imei号
        String deptId = event.getCompanyId();
        if (StringUtils.isEmpty(deptId)) {
            DeviceEntity device = deviceBiz.getById(imei);
            if (device != null) {
                deptId = device.getCompanyId(); //获取上报设备的所属公司id，用于数据推送
            }
        }
        BaseModel baseModel = new BaseModel(imei);
        baseModel.setMessageType(MessageType.TYPE_EVENTS);
        baseModel.setEvent(event);

        //获取该公司的通知设置
//        NotificationSettings notificationSettings = notificationSettingsBiz.selectById(rentId);
//        if (notificationSettings != null && notificationSettings.getSettingByType(event.getType())) {
        String msg = JSON.toJSONString(baseModel);
        if (StringUtils.isNotEmpty(deptId)) {
            pushMessageToDept(deptId, msg);
        }
        pushMessageToDevice(imei, msg);
//        }
    }

    public void notifyResult(CommandResult commandResult) {
        String imei = commandResult.getImei(); //获取推送数据的imei号
        BaseModel baseModel = new BaseModel(imei);
        baseModel.setMessageType(MessageType.TYPE_RESULTS);
        baseModel.setCommandResult(commandResult);
        String msg = JSON.toJSONString(baseModel);
        pushMessageToUser(commandResult.getUserId(), msg);
        pushMessageToDevice(imei, msg);
    }

    public void addUserSesstion(List<SysDeptEntity> depts, String userId, String sessionId) {
        Set<String> userS = userSet.computeIfAbsent(userId, k -> new HashSet<>());
        userS.add(sessionId);
        StringBuilder bb = new StringBuilder();
        for (SysDeptEntity de : depts) {
            Set<String> deptS = deptSet.computeIfAbsent(String.valueOf(de.getDeptId()), k -> new HashSet<>());
            deptS.add(sessionId);
            bb.append(de.getDeptId());
            bb.append(":");
        }
        if (bb.length() > 1) {
            String comId = bb.substring(0, bb.length() - 1);
            sessionIdAndUDD.put(sessionId, userId + "," + comId + ",0");
        } else {
            sessionIdAndUDD.put(sessionId, userId + "," + 0 + ",0");
        }
    }

    public void addDeviceSesstion(String imei, String sessionId) {
        Set<String> devS = deviceSet.computeIfAbsent(imei, k -> new HashSet<>());
        devS.add(sessionId);
        sessionIdAndUDD.put(sessionId, "0,0,"+imei);
    }

    public void removeSesstion(String sessionId) {
        String ses = sessionIdAndUDD.remove(sessionId);
        if (ses == null) {
            return;
        }
        String[] keys = ses.split(",");
        String userId = keys[0];
        String deptId = keys[1];
        String deviceId = keys[2];
        Set<String> uS = userSet.get(userId);
        if (uS != null) {
            uS.remove(sessionId);
        }

        String[] depts = deptId.split(":");
        for (String dd : depts) {
            Set<String> depS = deptSet.get(dd);
            if (depS != null) {
                depS.remove(sessionId);
            }
        }
        Set<String> devS = deviceSet.get(deviceId);
        if (devS != null) {
            devS.remove(sessionId);
        }
    }

    public void doRemoveSocket(List<WebSocket> webSocket) {
        for (WebSocket ws : webSocket) {
            String id = ws.getIdentifier();
            removeSesstion(id);
        }
    }
}
