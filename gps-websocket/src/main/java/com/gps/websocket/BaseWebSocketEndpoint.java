package com.gps.websocket;

import com.gps.websocket.utils.SpringContextHolder;
import com.gps.websocket.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.util.Date;

/**
 * NOTE: Nginx反向代理要支持WebSocket，需要配置几个header，否则连接的时候就报404
 * proxy_http_version 1.1;
 * proxy_set_header Upgrade $http_upgrade;
 * proxy_set_header Connection "upgrade";
 * proxy_read_timeout 3600s; //这个时间不长的话就容易断开连接
 *
 * @author xiongshiyan at 2018/10/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
/*@Component
@ServerEndpoint(value ="/websocket/connect/{identifier}")*/

/**
 * 写自己的Endpoint类，继承自此类，添加@ServerEndpoint、@Component注解，
 * 然后在方法中添加@OnOpen、@OnMessage、@OnClose、@OnError即可，这些方法中可以调用父类方法
 * @author xiongshiyan
 */
public abstract class BaseWebSocketEndpoint {
    /**
     * 路径标识：目前使用token来代表
     */
    public static final String IDENTIFIER = "identifier";
    protected static final Logger logger = LoggerFactory.getLogger(BaseWebSocketEndpoint.class);

    /// 无法通过这种方式注入组件
    /*@Autowired
    private WebSocketManager websocketManager;*/

    ///
    /*@OnOpen
    public void onOpen(Session session, @PathParam(IDENTIFIER) String identifier) {
        logger.info("*** WebSocket opened from sessionId " + session.getId() + " , identifier = " + identifier);
        connect(identifier, session);
    }*/
    ///
    /*@OnMessage
    public void onMessage(String message, Session session , @PathParam(IDENTIFIER) String identifier) {
        logger.info("接收到的数据为：" + message + " from sessionId " + session.getId() + " , identifier = " + identifier);
        receiveMessage(identifier, message, session);
    }*/
    ////
    /*@OnClose
    public void onClose(Session session , @PathParam(IDENTIFIER) String identifier) {
        logger.info("*** WebSocket closed from sessionId " + session.getId() + " , identifier = " + identifier);
        disconnect(identifier);
    }*/
    ///
    /*@OnError
    public void onError(Throwable t , @PathParam(IDENTIFIER) String identifier){
        logger.info("发生异常：, identifier = " + identifier);
        logger.error(t.getMessage() , t);
        disconnect(identifier);
    }*/

    public void connect(String identifier, Session session) {
        try {
            if (null == identifier || "".equals(identifier)) {
                return;
            }

            WebSocketManager websocketManager = getWebSocketManager();

            WebSocket webSocket = new WebSocket();
            webSocket.setIdentifier(identifier);
            webSocket.setSession(session);
            webSocket.setLastHeart(new Date());
            //像刷新这种，id一样，session不一样，后面的覆盖前面的
            websocketManager.put(identifier, webSocket);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void disconnect(String identifier) {
        getWebSocketManager().remove(identifier);
    }

    public void receiveMessage(String identifier, String message, Session session) {
        WebSocketManager webSocketManager = getWebSocketManager();
        //心跳监测
        if (webSocketManager.isPing(identifier, message)) {
            String pong = webSocketManager.pong(identifier, message);
            WebSocketUtil.sendMessage(session, pong);

            WebSocket webSocket = webSocketManager.get(identifier);
            //更新心跳时间
            if (null != webSocket) {
                webSocket.setLastHeart(new Date());
            }
            return;
        }
        //收到其他消息的时候
        webSocketManager.onMessage(identifier, message);
    }

    protected WebSocketManager getWebSocketManager() {
        return SpringContextHolder.getBean(WebSocketManager.WEBSOCKET_MANAGER_NAME, WebSocketManager.class);
    }
}
