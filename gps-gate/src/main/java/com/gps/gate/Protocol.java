package com.gps.gate;


import java.util.Collection;

public interface Protocol {

    String getName();
    int getPort();
    Collection<TrackerServer> getServerList();

}
