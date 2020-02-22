package com.gps.gate;


import com.gps.common.model.Position;
import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.util.Collection;

public abstract class BaseProtocolDecoder extends ExtendedObjectDecoder {

    private final Protocol protocol;

    public BaseProtocolDecoder(Protocol protocol) {
        this.protocol = protocol;
    }

    public String getProtocolName() {
        return protocol.getName();
    }

    @Override
    protected void onMessageEvent(
            Channel channel, SocketAddress remoteAddress, Object originalMessage, Object decodedMessage) {
        Position position = null;
        if (decodedMessage != null) {
            if (decodedMessage instanceof Position) {
                position = (Position) decodedMessage;
            } else if (decodedMessage instanceof Collection) {
                Collection positions = (Collection) decodedMessage;
                if (!positions.isEmpty()) {
                    position = (Position) positions.iterator().next();
                }
            }
        }
    }

    @Override
    protected Object handleEmptyMessage(Channel channel, SocketAddress remoteAddress, Object msg) {
        return null;
    }

}
