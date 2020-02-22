package com.gps.gate;


import java.net.SocketAddress;

public class NetworkMessage {

    private final SocketAddress remoteAddress;
    private final Object message;

    public NetworkMessage(Object message, SocketAddress remoteAddress) {
        this.message = message;
        this.remoteAddress = remoteAddress;
    }

    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public Object getMessage() {
        return message;
    }

}
