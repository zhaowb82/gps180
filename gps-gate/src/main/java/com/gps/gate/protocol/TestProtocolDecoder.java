
package com.gps.gate.protocol;

import com.alibaba.fastjson.JSON;
import com.gps.common.helper.UnitsConverter;
import com.gps.common.model.Position;
import com.gps.gate.BaseHttpProtocolDecoder;
import com.gps.gate.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestProtocolDecoder extends BaseHttpProtocolDecoder {

    public TestProtocolDecoder(Protocol protocol) {
        super(protocol);
    }

    protected Position decodeData(Channel channel, SocketAddress remoteAddress, Position position) {
        try {
            position.setValid(true);
//            position.setTime(DateUtil.getDateByStr(position.getLocTime(), "yyyy-MM-dd'T'HH:mm:ss"));
            position.setSpeed(UnitsConverter.knotsFromKph(position.getSpeed()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return position;
    }

    @Override
    protected Object decode(Channel channel, SocketAddress remoteAddress, Object msg) {
        List<Position> list = new ArrayList<>();
        try {
            if (msg instanceof FullHttpRequest) {
                FullHttpRequest request = (FullHttpRequest) msg;
                ByteBuf buffer = request.content();
                List<Position> ps = JSON.parseArray(buffer.toString(StandardCharsets.UTF_8), Position.class);
                list = ps.stream()
                        .map(c -> decodeData(channel, remoteAddress, c))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
            sendResponse(channel, HttpResponseStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
