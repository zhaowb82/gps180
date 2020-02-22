package com.gps.gate;


import io.netty.channel.ChannelHandler;

public interface PipelineBuilder {

    void addLast(ChannelHandler handler);

}