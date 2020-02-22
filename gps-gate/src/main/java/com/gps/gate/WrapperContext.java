package com.gps.gate;


import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;

import java.net.SocketAddress;

public class WrapperContext implements ChannelHandlerContext {

    private ChannelHandlerContext context;
    private SocketAddress remoteAddress;

    public WrapperContext(ChannelHandlerContext context, SocketAddress remoteAddress) {
        this.context = context;
        this.remoteAddress = remoteAddress;
    }

    @Override
    public Channel channel() {
        return context.channel();
    }

    @Override
    public EventExecutor executor() {
        return context.executor();
    }

    @Override
    public String name() {
        return context.name();
    }

    @Override
    public ChannelHandler handler() {
        return context.handler();
    }

    @Override
    public boolean isRemoved() {
        return context.isRemoved();
    }

    @Override
    public ChannelHandlerContext fireChannelRegistered() {
        return context.fireChannelRegistered();
    }

    @Override
    public ChannelHandlerContext fireChannelUnregistered() {
        return context.fireChannelUnregistered();
    }

    @Override
    public ChannelHandlerContext fireChannelActive() {
        return context.fireChannelActive();
    }

    @Override
    public ChannelHandlerContext fireChannelInactive() {
        return context.fireChannelInactive();
    }

    @Override
    public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
        return context.fireExceptionCaught(cause);
    }

    @Override
    public ChannelHandlerContext fireUserEventTriggered(Object evt) {
        return context.fireUserEventTriggered(evt);
    }

    @Override
    public ChannelHandlerContext fireChannelRead(Object msg) {
        if (!(msg instanceof NetworkMessage)) {
            msg = new NetworkMessage(msg, remoteAddress);
        }
        return context.fireChannelRead(msg);
    }

    @Override
    public ChannelHandlerContext fireChannelReadComplete() {
        return context.fireChannelReadComplete();
    }

    @Override
    public ChannelHandlerContext fireChannelWritabilityChanged() {
        return context.fireChannelWritabilityChanged();
    }

    @Override
    public ChannelFuture bind(SocketAddress localAddress) {
        return context.bind(localAddress);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress) {
        return context.connect(remoteAddress);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        return context.connect(remoteAddress, localAddress);
    }

    @Override
    public ChannelFuture disconnect() {
        return context.disconnect();
    }

    @Override
    public ChannelFuture close() {
        return context.close();
    }

    @Override
    public ChannelFuture deregister() {
        return context.deregister();
    }

    @Override
    public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
        return context.bind(localAddress, promise);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
        return context.connect(remoteAddress, promise);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        return context.connect(remoteAddress, localAddress, promise);
    }

    @Override
    public ChannelFuture disconnect(ChannelPromise promise) {
        return context.disconnect(promise);
    }

    @Override
    public ChannelFuture close(ChannelPromise promise) {
        return context.close(promise);
    }

    @Override
    public ChannelFuture deregister(ChannelPromise promise) {
        return context.deregister(promise);
    }

    @Override
    public ChannelHandlerContext read() {
        return context.read();
    }

    @Override
    public ChannelFuture write(Object msg) {
        return context.write(msg);
    }

    @Override
    public ChannelFuture write(Object msg, ChannelPromise promise) {
        if (!(msg instanceof NetworkMessage)) {
            msg = new NetworkMessage(msg, remoteAddress);
        }
        return context.write(msg, promise);
    }

    @Override
    public ChannelHandlerContext flush() {
        return context.flush();
    }

    @Override
    public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
        return context.writeAndFlush(msg, promise);
    }

    @Override
    public ChannelFuture writeAndFlush(Object msg) {
        return context.writeAndFlush(msg);
    }

    @Override
    public ChannelPromise newPromise() {
        return context.newPromise();
    }

    @Override
    public ChannelProgressivePromise newProgressivePromise() {
        return context.newProgressivePromise();
    }

    @Override
    public ChannelFuture newSucceededFuture() {
        return context.newSucceededFuture();
    }

    @Override
    public ChannelFuture newFailedFuture(Throwable cause) {
        return context.newFailedFuture(cause);
    }

    @Override
    public ChannelPromise voidPromise() {
        return context.voidPromise();
    }

    @Override
    public ChannelPipeline pipeline() {
        return context.pipeline();
    }

    @Override
    public ByteBufAllocator alloc() {
        return context.alloc();
    }

    @SuppressWarnings("deprecation")
    @Override
    public <T> Attribute<T> attr(AttributeKey<T> key) {
        return context.attr(key);
    }

    @SuppressWarnings("deprecation")
    @Override
    public <T> boolean hasAttr(AttributeKey<T> key) {
        return context.hasAttr(key);
    }

}
