
package com.gps.api.modules.cm.controller;

import com.gps.api.modules.cm.chinamobile.CmApi;
import com.gps.api.modules.cm.chinamobile.entity.ApnInfo;
import com.gps.api.modules.cm.chinamobile.entity.GroupInfo;
import com.gps.api.modules.cm.chinamobile.entity.MsisdnInfo;
import com.gps.db.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/sim3")
@Api(tags = "3方服务器调用接口")
public class Sim3rdController {
    @Autowired
    private CmApi cmApi;

//    @Login
//    @GetMapping("userInfo")
//    @ApiOperation(value = "获取用户信息", response = UserEntity.class)
//    public R userInfo(@ApiIgnore @LoginUser UserEntity user) {
//        return R.ok().put("user", user);
//    }

//    @Login
//    @GetMapping("userId")
//    @ApiOperation("获取用户ID")
//    public R userInfo(@ApiIgnore @RequestAttribute("userId") Integer userId) {
//        return R.ok().put("userId", userId);
//    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public R notToken() {
        Call<GroupInfo> aa = cmApi.getService().groupInfoQuery();
        try {
            GroupInfo aaa = aa.execute().body();
            return R.ok(aaa);
        } catch (IOException e) {
            e.printStackTrace();
            return R.ok().put("msg", "无需token也能访问。。。").put("e", e.getMessage());
        }
    }

    @GetMapping("qAll")
    @ApiOperation("全量号码的号码、ICCID、IMSI、IMEI信息互查")
    public R qAll() {
        Call<Map<String, Object>> aa = cmApi.getService().infoAllQuery();
        try {
            Map<String, Object> aaa = aa.execute().body();
            return R.ok().put("msg", aaa);
        } catch (IOException e) {
            e.printStackTrace();
            return R.ok().put("msg", "无需token也能访问。。。").put("e", e.getMessage());
        }
    }

    @GetMapping("iccidSingleQuery")
    @ApiOperation("单个号码的号码、ICCID、IMSI、IMEI信息互查")
    public R iccidSingleQuery(String iccid) {
        Call<MsisdnInfo> aa = cmApi.getService().iccidSingleQuery(null, iccid, null, null);
        try {
            MsisdnInfo aaa = aa.execute().body();
            return R.ok().put("msg", aaa);
        } catch (IOException e) {
            e.printStackTrace();
            return R.ok().put("msg", "无需token也能访问。。。").put("e", e.getMessage());
        }
    }

    @GetMapping("dailyflowRealtimeQuery")
    @ApiOperation("提供单个号码的月流量信息实时查询")
    public R dailyflowRealtimeQuery(String iccid) {
        Call<MsisdnInfo> aa = cmApi.getService().dailyflowRealtimeQuery(null, iccid);
        try {
            MsisdnInfo aaa = aa.execute().body();
            return R.ok().put("msg", aaa);
        } catch (IOException e) {
            e.printStackTrace();
            return R.ok().put("msg", "无需token也能访问。。。").put("e", e.getMessage());
        }
    }

    @GetMapping("monthflowRealtimeQuery")
    @ApiOperation("提供单个号码的当月/历史的月流量信息查询，最多能够查询最近6个月的月流量")
    public R monthflowRealtimeQuery(String iccid, String month) {
        Call<ApnInfo.ApnInfoList> aa = cmApi.getService().monthflowRealtimeQuery(null, iccid, month);
        try {
            ApnInfo.ApnInfoList aaa = aa.execute().body();
            return R.ok().put("msg", aaa);
        } catch (IOException e) {
            e.printStackTrace();
            return R.ok().put("msg", "无需token也能访问。。。").put("e", e.getMessage());
        }
    }

    @GetMapping("actitimeSingleQuery")
    @ApiOperation("查询单个号码的进入正使用期时间")
    public R actitimeSingleQuery(String iccid) {
        Call<MsisdnInfo> aa = cmApi.getService().actitimeSingleQuery(null, iccid);
        try {
            MsisdnInfo aaa = aa.execute().body();
            return R.ok().put("msg", aaa);
        } catch (IOException e) {
            e.printStackTrace();
            return R.ok().put("msg", "无需token也能访问。。。").put("e", e.getMessage());
        }
    }

}
