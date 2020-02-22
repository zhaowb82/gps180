package com.gps.gate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

@Slf4j
public class AppApplicationListener implements ApplicationListener {

    @Autowired
    private TCPServer tcpServer;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextClosedEvent) {
            log.info("================:{}", "ContextClosedEvent");
            tcpServer.stopServer();
        }
        if (event instanceof ApplicationReadyEvent) {
            log.info("================:{}", "ApplicationReadyEvent");
            tcpServer.startServer();
        }
    }

}
