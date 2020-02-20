package com.gps.api.ws;
import com.gps.api.modules.sys.entity.SysDeptEntity;
import com.gps.api.modules.sys.oauth2.GpsPrincipal;
import com.gps.api.modules.sys.service.SysDeptService;
import org.apache.shiro.SecurityUtils;
import com.gps.websocket.utils.SpringContextHolder;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;

/**
 * 在@ServerEndpoint注解里面添加configurator属性[@ServerEndpoint(value="/socketTest",configurator=GetHttpSessionConfigurator.class)]
 * 可以在OnOpen中通过HttpSession.class.getName()获取到http session,
 * 那么我们就可以使用session.getId()来作为标识
 * <pre>@OnOpen
    public void onOpen(Session session,EndpointConfig config) {
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        sessionMap.put(session.getId(), session);
    }</pre>
 {@link https://blog.csdn.net/huangbaokang/article/details/77579151?utm_source=blogxgwz0}
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        GpsPrincipal principal = (GpsPrincipal) SecurityUtils.getSubject().getPrincipal();
//        HttpSession httpSession = (HttpSession) request.getHttpSession();
//        sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
        if (principal == null) {
            return;
        }
        sec.getUserProperties().put("principal", principal);
        //sec.getUserProperties().put("name", "wb");
        if (principal.getUserEntity() != null) {
            SysDeptService sysDeptService = SpringContextHolder.getBean("sysDeptService", SysDeptService.class);
            List<SysDeptEntity> depts = sysDeptService.listByUser();
            sec.getUserProperties().put("depts", depts);
        }
        super.modifyHandshake(sec, request, response);
    }

}