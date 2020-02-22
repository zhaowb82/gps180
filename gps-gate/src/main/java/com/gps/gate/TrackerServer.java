package com.gps.gate;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetSocketAddress;

public abstract class TrackerServer {

    private final boolean datagram;
    private final AbstractBootstrap bootstrap;

    public boolean isDatagram() {
        return datagram;
    }

    public TrackerServer(boolean datagram, String protocol, int port) {
        this.datagram = datagram;
        this.port = port;
        BasePipelineFactory pipelineFactory = new BasePipelineFactory(this, protocol) {
            @Override
            protected void addProtocolHandlers(PipelineBuilder pipeline) {
                TrackerServer.this.addProtocolHandlers(pipeline);
            }
        };

        if (datagram) {
            this.bootstrap = new Bootstrap()
                    .group(EventLoopGroupFactory.getWorkerGroup())
                    .channel(NioDatagramChannel.class)
                    .handler(pipelineFactory);
        } else {
            this.bootstrap = new ServerBootstrap()
                    .group(EventLoopGroupFactory.getBossGroup(), EventLoopGroupFactory.getWorkerGroup())
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 360000)
                    .option(ChannelOption.SO_BACKLOG, 100000)
                    .option(ChannelOption.SO_RCVBUF, 30 * 1024)
                    .childHandler(pipelineFactory);
        }
    }

    protected abstract void addProtocolHandlers(PipelineBuilder pipeline);

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public void start() throws Exception {
        InetSocketAddress endpoint = new InetSocketAddress(port);
        Channel channel = bootstrap.bind(endpoint).sync().channel();
        if (channel != null) {
            getChannelGroup().add(channel);
        }
    }

    public void stop() {
        channelGroup.close().awaitUninterruptibly();
    }

}
