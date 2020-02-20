package com.gps.api.modules.cm.luat;

import com.gps.api.modules.cm.luat.entity.SendSmsRes;
import com.gps.api.modules.cm.luat.entity.SimInfo;
import com.gps.api.modules.cm.luat.entity.UsageLog;
//import cm.luat.api.HttpApi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;
import java.util.Map;

//@HttpApi(value = "IM_BASE_URL")//, interceptor = {ImInterceptor.class}
public interface LuatApiService {

    /**
     *  物联卡指定日期用量日志查询，支持查询最近七日流量
     */
    @POST("sim/iotcard/usagelog")
    Call<List<UsageLog>> usageLog(@Body Map<String, Object> body);
//    @FormUrlEncoded
//    @POST("sim/iotcard/usagelog")
//    Call<UsageLog> usageLog(@Field("iccids") String iccids, @Field("query_date") String query_date);

    /**
     * 物联卡月流量日志
     */
    @POST("sim/iotcard/monthly/usagelog")
    Call<List<UsageLog>> usageLogM(@Body Map<String, Object> body);

    /**
     * 获取物联卡信息
     */
    @POST("sim/iotcard/card")
    Call<SimInfo> cardInfo(@Body Map<String, Object> body);
    /**
     * 获取物联卡信息
     */
    @POST("sim/iotcard/cards")
    Call<SimInfo.Sims> cardsInfo(@Body Map<String, Object> body);

    /**
     * 获取物联卡信息
     */
    @POST("sim/iotcard/sms_submit")
    Call<SendSmsRes> sendSms(@Body Map<String, Object> body);

}
