package com.gps.gate.handler;


import com.alibaba.fastjson.JSONObject;
import com.gps.common.Constants;
import com.gps.common.MessageType;
import com.gps.common.model.BaseModel;
import com.gps.common.model.Position;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
@Scope("prototype")
@ChannelHandler.Sharable
public class MainDataHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainDataHandler.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public MainDataHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object decodedMessage) throws Exception {
        if (decodedMessage != null) {
            BaseModel baseModel = new BaseModel();
            baseModel.setServerTime(new Date());
            if (decodedMessage instanceof Position) {
                Position position = (Position) decodedMessage;
                baseModel.setImei(position.getImei());
                baseModel.setMessageType(MessageType.TYPE_POSITIONS);
                baseModel.setPosition(position);
                pushMessage(baseModel);
            } else if (decodedMessage instanceof Collection) {
                List<Position> positions = (List) decodedMessage;
                if (positions.size() > 0) {
                    baseModel.setImei(positions.get(0).getImei());
                    baseModel.setMessageType(MessageType.TYPE_POSITIONS);
                    baseModel.setPositions(positions);

                    pushMessage(baseModel);
                }
            } else if (decodedMessage instanceof BaseModel) {
                BaseModel model = (BaseModel) decodedMessage;
                pushMessage(model);
                return;
            }
            if (decodedMessage instanceof Collection) {
                for (Object o : (Collection) decodedMessage) {
                    ctx.fireChannelRead(o);
                }
            } else {
                ctx.fireChannelRead(decodedMessage);
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    public void pushMessage(BaseModel model) {
        String message = JSONObject.toJSONString(model);
        LOGGER.info(message);
        kafkaTemplate.send(Constants.POSITION_TOPIC, Constants.POS_KEY, message);
    }
}
