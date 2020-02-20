package com.gps.api.ws;

import com.gps.api.modules.sys.entity.SysDeptEntity;
import com.gps.api.modules.sys.entity.SysUserEntity;
import com.gps.api.modules.sys.oauth2.GpsPrincipal;
import com.gps.db.entity.DeviceEntity;
import com.gps.websocket.BaseWebSocketEndpoint;
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
 * @author xiongshiyan
 */
@Component
@ServerEndpoint(value = "/websocket/{identifier}", configurator = GetHttpSessionConfigurator.class)
public class WebSocketEndpoint extends BaseWebSocketEndpoint {

    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam(IDENTIFIER) String identifier) throws IOException {
        logger.info("*** WebSocket opened from sessionId " + session.getId() + " , identifier = " + identifier + " hash=" + this.hashCode());
        GpsPrincipal principal = (GpsPrincipal) session.getUserProperties().get("principal");
        if (principal == null) {
            session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "404"));
            return;
        }
        WebSocketPush webSocketPush = SpringContextHolder.getBean("webSocketPush", WebSocketPush.class);
        this.session = session;
        identifier = session.getId();
        connect(identifier, session);
        SysUserEntity u = principal.getUserEntity();
        DeviceEntity d = principal.getDeviceEntity();
        if (u != null) {
            Long depId = u.getDeptId();
            Long uId = u.getUserId();

            List<SysDeptEntity> depts = (List<SysDeptEntity>) session.getUserProperties().get("depts");
            webSocketPush.addUserSesstion(depts, String.valueOf(uId), identifier);
        } else if (d != null) {
            String comId = d.getCompanyId();
            String imei = d.getImei();
            webSocketPush.addDeviceSesstion(imei, identifier);
        }
    }

    ///
    @OnMessage
    public void onMessage(String message, Session session, @PathParam(IDENTIFIER) String identifier) {
        logger.info("接收到的数据为：" + message + " from sessionId " + session.getId() + " , identifier = " + identifier);
        identifier = session.getId();
        receiveMessage(identifier, message, session);
    }

    ////
    @OnClose
    public void onClose(Session session, @PathParam(IDENTIFIER) String identifier) {
        logger.info("*** WebSocket closed from sessionId " + session.getId() + " , identifier = " + identifier);
        identifier = session.getId();
        disconnect(identifier);
        try {
            WebSocketPush webSocketPush = SpringContextHolder.getBean("webSocketPush", WebSocketPush.class);
            webSocketPush.removeSesstion(identifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///
    @OnError
    public void onError(Throwable t, @PathParam(IDENTIFIER) String identifier) {
        logger.info("发生异常：, identifier = " + identifier);
        logger.error(t.getMessage(), t);
        identifier = session.getId();
        disconnect(identifier);
        WebSocketPush webSocketPush = SpringContextHolder.getBean("webSocketPush", WebSocketPush.class);
        webSocketPush.removeSesstion(identifier);
    }
}
