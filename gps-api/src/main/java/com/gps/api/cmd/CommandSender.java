package com.gps.api.cmd;

import com.alibaba.fastjson.JSON;
import com.gps.common.Constants;
import com.gps.common.cache.CacheKeys;
import com.gps.common.future.SyncFuture;
import com.gps.common.model.Command;
import com.gps.common.model.CommandType;
import com.gps.db.GpsRedisUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CommandSender {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private GpsRedisUtils gpsRedisUtils;

    private SyncFuture<List<CommandType>> future;

    public void sendCommand(Command command) {
        try {
            int expireTime = 86400; // 1Day
            if (command.isSync()) {
                expireTime = 60;
            }
            gpsRedisUtils.set(CacheKeys.getCommandKeys(command.getType(), command.getImei()), command, expireTime);
            sendByKafka(command);
//            sendByApi(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CommandType> waitCmds() {
        try {
            future = new SyncFuture<>();
            return future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void putCmds(List<CommandType> cmds) {
        if (future != null) {
            future.setResponse(cmds);
            future = null;
        }
    }

    private void sendByKafka(Command command) {
        String message1 = JSON.toJSONString(command);
        kafkaTemplate.send(Constants.COMMANDS_TOPIC, Constants.CMD_KEY, message1);
    }

    private void sendByApi(Command command) {
        String message1 = JSON.toJSONString(command);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = RequestBody.create(message1, MediaType.parse("application/json;charset=UTF-8"));
        Request request = new Request.Builder()
                .url("http://api.1-blog.com/api/apiControl")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
