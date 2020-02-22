package com.gps.gate;

import com.gps.gate.protocol.TestProtocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class TCPServer {
    private final List<TrackerServer> serverList = new LinkedList<>();
    private final Map<String, BaseProtocol> protocolList = new ConcurrentHashMap<>();

    public TCPServer() throws Exception {
        BaseProtocol protocol = TestProtocol.class.newInstance();
        serverList.addAll(protocol.getServerList());
        protocolList.put(protocol.getName(), protocol);
    }

    public BaseProtocol getProtocol(String name) {
        return protocolList.get(name);
    }

    public void startServer() {
        log.info("=============================begin start server===================");
        for (TrackerServer server: serverList) {
            try {
                server.start();
                log.info("start port end: " + server.getPort());
            } catch (Exception e) {
                log.warn("Port {} is disabled due to conflict", server.getPort());
            }
        }
        log.info("=============================end start server===================");
    }

    public void stopServer() {
        for (TrackerServer server: serverList) {
            log.info("server stop: " + server.getPort());
            server.stop();
        }
    }
}
