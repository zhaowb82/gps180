package com.gps.api.ws.evtlisten;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.gps.websocket.WebSocketCloseEvent;

@Component
public class WebSocketCloseEventListener implements ApplicationListener<WebSocketCloseEvent> {

//    @Resource
//    private ISmsLogService smsLogServiceImpl;
//
//    @Resource
//    private ISmsSender yunPianSmsSender;
//
//    /**
//     * @param smsLog status 0成功，非0失败
//     */
    @Override
    @Async
    public void onApplicationEvent(WebSocketCloseEvent event) {
//        SmsLog smsLog = event.getSmsSource();
//        CallResult result = yunPianSmsSender.send(smsLog.getSmsText(), smsLog.getUserPhone());
//        smsLog.setSendStatus(result.getStatus());
//        smsLog.setSendTime(new Date());
//        if (event.isPersistent()) {
//            try {
//                smsLogServiceImpl.save(smsLog);
//            } catch (Exception e) {
//                logger.error("发送短信出" + e.getMessage(), e);
//            }
//        }
    }

}
