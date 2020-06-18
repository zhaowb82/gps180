package com.gps.api.ws;

import com.gps.api.modules.sys.entity.SysDeptEntity;
import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.api.modules.sys.oauth2.GpsPrincipal;
import com.gps.api.modules.sys.service.SysDeptService;
import com.gps.db.entity.DeviceEntity;
import com.gps.websocket.BaseWebSocketEndpoint;
import com.gps.websocket.utils.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.gps.websocket.utils.SpringContextHolder;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;

/**
 * 1.继承自 BaseWebSocketEndpoint
 * 2.标注@Component @ServerEndpoint
 *
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket/{identifier}", configurator = GetHttpSessionConfigurator.class)
public class WebSocketEndpoint extends BaseWebSocketEndpoint {

    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam(IDENTIFIER) String identifier) {
        log.debug("*** WebSocket opened from sessionId " + session.getId() + " , identifier = " + identifier + " hash=" + this.hashCode());
        GpsPrincipal principal = (GpsPrincipal) session.getUserProperties().get("principal");
        this.session = session;
        if (principal == null) {
            WebSocketUtil.sendMessage(session, "needAuth");
            return;
        }
        registSession(session, principal);
    }

    private void registSession(Session session, GpsPrincipal principal) {
        WebSocketPush webSocketPush = SpringContextHolder.getBean("webSocketPush", WebSocketPush.class);
        String identifier = session.getId();
        connect(identifier, session);
        SysUserEntity u = principal.getUserEntity();
        DeviceEntity d = principal.getDeviceEntity();
        if (u != null) {
            Long depId = u.getDeptId();
            Long uId = u.getUserId();
            SysDeptService sysDeptService = SpringContextHolder.getBean("sysDeptService", SysDeptService.class);
            List<SysDeptEntity> depts = sysDeptService.depsByUserId(uId);
            webSocketPush.addUserSesstion(depts, String.valueOf(uId), identifier);
        } else if (d != null) {
            String depId = d.getCompanyId();
            String imei = d.getImei();
            webSocketPush.addDeviceSesstion(imei, identifier);
        }
        WebSocketUtil.sendMessage(session, "loginId:" + identifier);
    }

    ///
    @OnMessage
    public void onMessage(String message, Session session, @PathParam(IDENTIFIER) String identifier) {
        log.debug("接收到的数据为：" + message + " from sessionId " + session.getId() + " , identifier = " + identifier);
        identifier = session.getId();
        try {
            receiveMessage(identifier, message, session);
        } catch (Exception e) {
            log.error("onMessage:" + e.getMessage(), e);
        }
    }

    ////
    @OnClose
    public void onClose(Session session, @PathParam(IDENTIFIER) String identifier) {
        log.debug("*** WebSocket closed from sessionId " + session.getId() + " , identifier = " + identifier);
        identifier = session.getId();
        disconnect(identifier);
        try {
            WebSocketPush webSocketPush = SpringContextHolder.getBean("webSocketPush", WebSocketPush.class);
            webSocketPush.removeSesstion(identifier);
        } catch (Exception e) {
            log.error("onClose:" + e.getMessage(), e);
//            e.printStackTrace();
        }
    }

    ///
    @OnError
    public void onError(Throwable t, @PathParam(IDENTIFIER) String identifier) {
        log.error("发生异常：, identifier ={}, {} ", identifier, session.getId());
        identifier = session.getId();
        disconnect(identifier);
        WebSocketPush webSocketPush = SpringContextHolder.getBean("webSocketPush", WebSocketPush.class);
        webSocketPush.removeSesstion(identifier);
    }
}
