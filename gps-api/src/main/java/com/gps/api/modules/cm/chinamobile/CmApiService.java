package com.gps.api.modules.cm.chinamobile;

import com.gps.api.modules.cm.chinamobile.entity.ApnInfo;
import com.gps.api.modules.cm.chinamobile.entity.GroupInfo;
import com.gps.api.modules.cm.chinamobile.entity.MsisdnInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Map;

public interface CmApiService {

    /**
     * 查询集团基本信息，包括成员数、各生命周期成员数等
     */
    @GET("router?method=triopi.group.info.query&groupCode=2002665535")
    Call<GroupInfo> groupInfoQuery();

    /**
     * 全量号码的号码、ICCID、IMSI、IMEI信息互查
     */
    @POST("router?method=triopi.member.iccid.all.query&groupCode=2002665535")
    Call<Map<String, Object>> iccidAllQuery();

    /**
     * 查询全量号码的信息，包括开户时间、APN、套餐订购信息等
     */
    @POST("router?method=triopi.member.info.all.query&groupCode=2002665535")
    Call<Map<String, Object>> infoAllQuery();

    /**
     * 单个号码的号码、ICCID、IMSI、IMEI信息互查
     */
    @GET("router?method=triopi.member.iccid.single.query")
    Call<MsisdnInfo> iccidSingleQuery(@Query("msisdn") String msisdn, @Query("iccid") String iccid,
                                      @Query("imsi") String imsi, @Query("imei") String imei);

    /**
     * 提供单个号码的月流量信息实时查询
     */
    @GET("router?method=triopi.member.dailyflow.realtime.query")
    Call<MsisdnInfo> dailyflowRealtimeQuery(@Query("msisdn") String msisdn, @Query("iccid") String iccid);

    /**
     * 提供单个号码的当月/历史的月流量信息查询，最多能够查询最近6个月的月流量
     */
    @GET("router?method=triopi.member.monthflow.realtime.query")
    Call<ApnInfo.ApnInfoList> monthflowRealtimeQuery(@Query("msisdn") String msisdn, @Query("iccid") String iccid,
                                                     @Query("month") String month);//yyyyMM 空值取当月

    /**
     * 查询单个号码的进入正使用期时间
     */
    @GET("router?method=triopi.member.actitime.single.query")
    Call<MsisdnInfo> actitimeSingleQuery(@Query("msisdn") String msisdn, @Query("iccid") String iccid);
}
