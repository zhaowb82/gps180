package com.gps.gate;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public abstract class BaseHttpProtocolDecoder extends BaseProtocolDecoder {

    public BaseHttpProtocolDecoder(Protocol protocol) {
        super(protocol);
    }

    public void sendResponse(Channel channel, HttpResponseStatus status) {
        if (channel != null) {
            HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
            response.headers().add(HttpHeaderNames.CONTENT_LENGTH, 0);
            channel.writeAndFlush(new NetworkMessage(response, channel.remoteAddress()));
        }
    }

}
