package com.gps.api.ws;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gps.api.cmd.CommandSender;
import com.gps.common.Constants;
import com.gps.common.model.CommandType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.gps.common.model.CommandResult;
import com.gps.common.model.Event;
import com.gps.common.model.Position;

import java.util.List;

@Slf4j
@Component
public class WsListener {

    @Autowired
    private WebSocketPush webSocketPush;
    @Autowired
    private CommandSender commandSender;

//    @KafkaListener(topics = "pos_event_device")
    @KafkaListener(topics = {"${kafka.consumer.topic}"})
    //   @KafkaListener(topicPartitions = {@TopicPartition(topic = "pos_event_device", partitionOffsets = @PartitionOffset(partition = "21", initialOffset = "34622143"))})
    public void listen(ConsumerRecord<?, ?> record) {
        String recordKey = record.key().toString();
        String obj = record.value().toString();
//        LOGGER.info("recordKey: " + recordKey + " val:" + obj);
        if ("dev".equals(recordKey)) {
//            Device device = JSONObject.parseObject(obj, Device.class);
//            webSocketPush.notifyDevState(device);
        } else if ("pos".equals(recordKey)) {
            Position position = JSONObject.parseObject(obj, Position.class);
            webSocketPush.notifyPos(position);
        } else if ("evt".equals(recordKey)) {
            Event event = JSONObject.parseObject(obj, Event.class);
            webSocketPush.notifyEvt(event);
        } else if ("result".equals(recordKey)) {
            CommandResult result = JSONObject.parseObject(obj, CommandResult.class);
            webSocketPush.notifyResult(result);
        }
    }

    @KafkaListener(topics = Constants.IMPORTCMD_TOPIC)
    public void listenImportCmd(ConsumerRecord<?, ?> record) {
        String recordKey = record.key().toString();
        String obj = record.value().toString();
        if (Constants.CMD_KEY.equals(recordKey)) {
            List<CommandType> cmds = JSONObject.parseObject(obj, new TypeReference<List<CommandType>>(){});
            commandSender.putCmds(cmds);
        }
    }
}
