package com.gps.api.modules.cm.service;

import com.gps.api.modules.cm.entity.Channel;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {
    public Channel selectByChannelCode(String channelCode) {
        Channel channel = new Channel();
        channel.setEncryptKey("111");
        return channel;
    }
}
