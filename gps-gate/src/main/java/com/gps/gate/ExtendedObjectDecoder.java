package com.gps.gate;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.net.SocketAddress;

public abstract class ExtendedObjectDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object originalMessage = null;
        try {
            NetworkMessage networkMessage = (NetworkMessage) msg;
            originalMessage= networkMessage.getMessage();

            Object decodedMessage = decode(ctx.channel(), networkMessage.getRemoteAddress(), originalMessage);

            onMessageEvent(ctx.channel(), networkMessage.getRemoteAddress(), originalMessage, decodedMessage);
            if (decodedMessage == null) {
                decodedMessage = handleEmptyMessage(ctx.channel(), networkMessage.getRemoteAddress(), originalMessage);
            }
            if (decodedMessage != null) {
                ctx.fireChannelRead(decodedMessage);
            }
        } finally {
            if (originalMessage != null) {
                ReferenceCountUtil.release(originalMessage);
            }
        }
    }

    protected void onMessageEvent(
            Channel channel, SocketAddress remoteAddress, Object originalMessage, Object decodedMessage) {
    }

    protected Object handleEmptyMessage(Channel channel, SocketAddress remoteAddress, Object msg) {
        return null;
    }

    protected abstract Object decode(Channel channel, SocketAddress remoteAddress, Object msg) throws Exception;

}

