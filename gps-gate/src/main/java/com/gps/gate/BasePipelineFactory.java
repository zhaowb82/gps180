package com.gps.gate;


import com.gps.gate.handler.MainDataHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class BasePipelineFactory extends ChannelInitializer<Channel> {

    private final TrackerServer server;
    private final String protocol;
    private int timeout = 0;

    private MainDataHandler dataHandler;
    private static final class OpenChannelHandler extends ChannelDuplexHandler {

        private final TrackerServer server;

        private OpenChannelHandler(TrackerServer server) {
            this.server = server;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            server.getChannelGroup().add(ctx.channel());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            server.getChannelGroup().remove(ctx.channel());
        }

    }

    private static class NetworkMessageHandler extends ChannelDuplexHandler {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            if (ctx.channel() instanceof DatagramChannel) {
                DatagramPacket packet = (DatagramPacket) msg;
                ctx.fireChannelRead(new NetworkMessage(packet.content(), packet.sender()));
            } else if (msg instanceof ByteBuf) {
                ByteBuf buffer = (ByteBuf) msg;
                ctx.fireChannelRead(new NetworkMessage(buffer, ctx.channel().remoteAddress()));
            }
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
            if (msg instanceof NetworkMessage) {
                NetworkMessage message = (NetworkMessage) msg;
                if (ctx.channel() instanceof DatagramChannel) {
                    InetSocketAddress recipient = (InetSocketAddress) message.getRemoteAddress();
                    InetSocketAddress sender = (InetSocketAddress) ctx.channel().localAddress();
                    ctx.write(new DatagramPacket((ByteBuf) message.getMessage(), recipient, sender), promise);
                } else {
                    ctx.write(message.getMessage(), promise);
                }
            } else {
                ctx.write(msg, promise);
            }
        }

    }

    public BasePipelineFactory(TrackerServer server, String protocol) {
        this.server = server;
        this.protocol = protocol;
        dataHandler = new MainDataHandler();
    }

    protected abstract void addProtocolHandlers(PipelineBuilder pipeline);

    private void addHandlers(ChannelPipeline pipeline, ChannelHandler... handlers) {
        for (ChannelHandler handler : handlers) {
            if (handler != null) {
                pipeline.addLast(handler);
            }
        }
    }

    public static <T extends ChannelHandler> T getHandler(ChannelPipeline pipeline, Class<T> clazz) {
        for (Map.Entry<String, ChannelHandler> handlerEntry : pipeline) {
            ChannelHandler handler = handlerEntry.getValue();
            if (handler instanceof WrapperInboundHandler) {
                handler = ((WrapperInboundHandler) handler).getWrappedHandler();
            } else if (handler instanceof WrapperOutboundHandler) {
                handler = ((WrapperOutboundHandler) handler).getWrappedHandler();
            }
            if (clazz.isAssignableFrom(handler.getClass())) {
                return (T) handler;
            }
        }
        return null;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        final ChannelPipeline pipeline = channel.pipeline();
        if (timeout > 0 && !server.isDatagram()) {
            pipeline.addLast(new IdleStateHandler(timeout, 30, 0, TimeUnit.SECONDS));
        }
        pipeline.addLast(new OpenChannelHandler(server));
        pipeline.addLast(new NetworkMessageHandler());

        addProtocolHandlers(handler -> {
            if (!(handler instanceof BaseProtocolDecoder || handler instanceof BaseProtocolEncoder)) {
                if (handler instanceof ChannelInboundHandler) {
                    handler = new WrapperInboundHandler((ChannelInboundHandler) handler);
                } else {
                    handler = new WrapperOutboundHandler((ChannelOutboundHandler) handler);
                }
            }
            pipeline.addLast(handler);
        });
        addHandlers(pipeline, dataHandler);
    }

}
