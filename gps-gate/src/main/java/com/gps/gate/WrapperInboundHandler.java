package com.gps.gate;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public class WrapperInboundHandler implements ChannelInboundHandler {

    private ChannelInboundHandler handler;

    public ChannelInboundHandler getWrappedHandler() {
        return handler;
    }

    public WrapperInboundHandler(ChannelInboundHandler handler) {
        this.handler = handler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        handler.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        handler.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        handler.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        handler.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof NetworkMessage) {
            NetworkMessage nm = (NetworkMessage) msg;
            handler.channelRead(new WrapperContext(ctx, nm.getRemoteAddress()), nm.getMessage());
        } else {
            handler.channelRead(ctx, msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        handler.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        handler.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        handler.channelWritabilityChanged(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        handler.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        handler.handlerRemoved(ctx);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        handler.exceptionCaught(ctx, cause);
    }

}

