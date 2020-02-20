/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 *
 * https://www.gps180.com
 *
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gps.api.common.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("devmgr")
@Api(tags = "设备管理，升级，日志")
@Slf4j
public class DevMgrController extends AbstractController {

	/////////////////////////////////////////////////////////////////
	@Data
	private static class UpData {
		private String oldver;
		private String canupver;
	}

	@GetMapping(value = "/upgrade")
	@ApiOperation(value = "升级测试")
	@SysLog("升级测试")
	public ResponseEntity<byte[]> fupgrade(String project_key, String imei, String device_key,
										   String firmware_name, String version, String phone) throws Exception {
		log.info("fupgrade: {}->{}:{}->{}", imei,phone,firmware_name,version);
		String path = null;
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")){
			path = "E://";
		} else {
		    path = "/up";
		}
		File jsonfile = new File(path + "/up.json");
		String jsonString = FileUtils.readFileToString(jsonfile, "GBK");
		jsonString = jsonString.replace("\r\n", "");
		ObjectMapper mapper = new ObjectMapper();
		List<UpData> upList = mapper.readValue(jsonString, new TypeReference<List<UpData>>() {});
		String needUpVer = null;
		for (UpData u : upList) {
			if (StringUtils.equals(version, u.getOldver())) {
				needUpVer = u.getCanupver();
				break;
			}
		}
		if (needUpVer == null) {
			HttpStatus statusCode = HttpStatus.BAD_REQUEST;
			ResponseEntity<byte[]> entity = new ResponseEntity<>(statusCode);
			return entity;
		}
		File dstFile = null;
		File file = new File(path);//"E://BJX-B91_1.0.6_Luat_V0034_8955_SSL_FLOAT.bin"
		File[] files = file.listFiles();
		for (File file2 : files) {
			if (file2.isDirectory()) {
				continue;
			}
			if (file2.isFile()) {
				String fName = file2.getName();
				String pattern = "_(\\d{1,2}\\.\\d{1,2}\\.\\d{1,2})_";
				// 创建 Pattern 对象
				Pattern r = Pattern.compile(pattern);
				// 现在创建 matcher 对象
				Matcher m = r.matcher(fName);
				if (m.find()) {
//                    System.out.println("Found value: " + m.group(0) );
					String v = m.group(1);
					if (StringUtils.equals(v, needUpVer)) {
						dstFile = file2;
						break;
					}
				}
			}
		}
		if (dstFile == null) {
			HttpStatus statusCode = HttpStatus.BAD_REQUEST;
			ResponseEntity<byte[]> entity = new ResponseEntity<>(statusCode);
			return entity;
		}
		log.info("fupgrade: download {}", dstFile.getName());
		InputStream is = new FileInputStream(dstFile);
		byte[] body = new byte[is.available()];
		is.read(body);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attchement;filename=" + dstFile.getName());
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> entity = new ResponseEntity<>(body, headers, statusCode);
		return entity;
	}

	@PostMapping(value = "/errUp")
	@ApiOperation(value = "错误日志上传")
//	@SysLog("错误日志上传")
	public String errUp(@RequestBody String body) {
		log.info("errUp: {}", body);
		return "";
	}

}
