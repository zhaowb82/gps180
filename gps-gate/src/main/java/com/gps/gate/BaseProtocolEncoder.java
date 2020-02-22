package com.gps.gate;

import com.gps.common.model.Command;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public abstract class BaseProtocolEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        NetworkMessage networkMessage = (NetworkMessage) msg;
        if (networkMessage.getMessage() instanceof Command) {
            Command command = (Command) networkMessage.getMessage();
            Object encodedCommand = encodeCommand(ctx.channel(), command);
            ctx.write(new NetworkMessage(encodedCommand, networkMessage.getRemoteAddress()), promise);
        } else {
            super.write(ctx, msg, promise);
        }
    }

    protected Object encodeCommand(Channel channel, Command command) {
        return encodeCommand(command);
    }

    protected Object encodeCommand(Command command) {
        return null;
    }

}

