
package com.gps.api.modules.cm.controller;

import com.gps.api.modules.sys.controller.AbstractController;
import com.gps.api.modules.cm.luat.LuatApi;
import com.gps.api.modules.cm.luat.entity.SendSmsRes;
import com.gps.api.modules.cm.luat.entity.SimInfo;
import com.gps.api.modules.cm.luat.entity.UsageLog;
import com.gps.db.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sim")
@Api(tags = "3方卡操作接口")
@Slf4j
public class SimController extends AbstractController {

	@Autowired
//	@HttpService
	private LuatApi luatApi;

	@GetMapping(value = "usageLog")
	@ApiOperation(value = "")
	public R usageLog(String iccids, String date) {
		Map<String, Object> body = new HashMap<>();
		body.put("iccids", iccids);
		body.put("query_date", date);//YYYYMMDD
		Call<List<UsageLog>> aa = luatApi.getService().usageLog(body);
		try {
			List<UsageLog> aaa = aa.execute().body();
			return R.ok(aaa);
		} catch (Exception e) {
//			e.printStackTrace();
			return R.error(e.getMessage());
		}
	}

	@GetMapping(value = "usageLogM")
	@ApiOperation(value = "")
	public R usageLogM(String iccids, String date) {
		Map<String, Object> body = new HashMap<>();
		body.put("iccids", iccids);
		body.put("query_month", date);//YYYYMMDD
		Call<List<UsageLog>> aa = luatApi.getService().usageLogM(body);
		try {
			List<UsageLog> aaa = aa.execute().body();
			return R.ok(aaa);
		} catch (Exception e) {
//			e.printStackTrace();
			return R.error(e.getMessage());
		}
	}
	@GetMapping(value = "simInfo")
	@ApiOperation(value = "")
	public R simInfo(String iccid) {
		Map<String, Object> body = new HashMap<>();
		body.put("iccid", iccid);
		Call<SimInfo> aa = luatApi.getService().cardInfo(body);
		try {
			SimInfo aaa = aa.execute().body();
			return R.ok(aaa);
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
	}
	@GetMapping(value = "simsInfo")
	@ApiOperation(value = "")
	public R simsInfo(String iccids) {
		Map<String, Object> body = new HashMap<>();
		body.put("iccids", iccids);
		Call<SimInfo.Sims> aa = luatApi.getService().cardsInfo(body);
		try {
			SimInfo.Sims aaa = aa.execute().body();
			return R.ok(aaa);
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
	}
	@PostMapping(value = "sendSms")
	@ApiOperation(value = "sendSms")
	public R sendSms(String msisdns, String content) {
		Map<String, Object> body = new HashMap<>();
		body.put("msisdns", msisdns);
		body.put("content", content);
		Call<SendSmsRes> aa = luatApi.getService().sendSms(body);
		try {
			SendSmsRes aaa = aa.execute().body();
			return R.ok(aaa);
		} catch (Exception e) {
			return R.error(e.getMessage());
		}
	}

	@Data
	public static class SmsCb {
		String msisdn;
		int status;//发送状态,0发送中 1成功 2失败
		int sms_id;//平台的唯一短信id
	}

	@PostMapping("smscb")
	public R postion(@RequestBody SmsCb cb) {

		return R.ok();
	}

//
//	@PostMapping(value = "addProduct")
//	@ApiOperation(value = "")
//	public R addProduct(@RequestBody ProductEntity productEntity) {
//
//		return R.ok();
//	}
//
//	@PostMapping(value = "updateProduct")
//	@ApiOperation(value = "")
//	public R updateProduct(@RequestBody ProductEntity productEntity) {
//
//		return R.ok();
//	}


}
