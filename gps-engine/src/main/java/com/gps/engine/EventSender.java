package com.gps.engine;

import com.alibaba.fastjson.JSONObject;
import com.gps.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent(String key, Object obj) {
        sendByKafka(key, obj);
    }

    private void sendByKafka(String key, Object obj) {
        try {
            kafkaTemplate.send(Constants.NOTIFYUI_TOPIC, key, JSONObject.toJSONString(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
