package com.gps.gate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseProtocol implements Protocol {

    private final String name;
    protected int port;
    private final List<TrackerServer> serverList = new LinkedList<>();

    public static String nameFromClass(Class<?> clazz) {
        String className = clazz.getSimpleName();
        return className.substring(0, className.length() - 8).toLowerCase();
    }

    public BaseProtocol() {
        name = nameFromClass(getClass());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPort() {
        return port;
    }

    protected void addServer(TrackerServer server) {
        serverList.add(server);
    }

    @Override
    public Collection<TrackerServer> getServerList() {
        return serverList;
    }

}
