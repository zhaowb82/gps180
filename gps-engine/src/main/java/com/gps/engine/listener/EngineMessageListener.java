package com.gps.engine.listener;

import com.alibaba.fastjson.JSONObject;

import com.gps.common.Constants;
import com.gps.engine.TrackerServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.gps.common.model.BaseModel;

@Slf4j
@Component
public class EngineMessageListener {

    @Autowired
    private TrackerServer server;

    @KafkaListener(topics = {"${kafka.consumer.topic}"})
    public void kafkaListener(ConsumerRecord<?, ?> record) {
        try {
            String recordKey = record.key().toString();
            if (Constants.POS_KEY.equals(recordKey)) {
                BaseModel baseModel = JSONObject.parseObject(record.value().toString(), BaseModel.class);
                server.postData(baseModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
