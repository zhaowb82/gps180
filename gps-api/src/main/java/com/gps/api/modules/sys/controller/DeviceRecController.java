package com.gps.api.modules.sys.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gps.api.common.annotation.SysLog;
import com.gps.common.helper.BytesUtil;
import com.gps.db.entity.DeviceRecEntity;
import com.gps.db.service.DeviceRecService;
import com.gps.db.utils.R;
import com.gps.db.utils.UIDGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gps.db.dbutils.MyPage;

import javax.servlet.http.HttpServletRequest;


/**
 * @author zb
 * @email zb@gmail.com
 * @date 2020-01-22 16:33:18
 */
@RestController
@RequestMapping("devicerec")
@Api(tags = "GPS设备录音管理")
public class DeviceRecController {
    @Autowired
    private DeviceRecService deviceRecService;

    /**
     * 列表
     */
    @GetMapping("list")
    @RequiresPermissions("devicerec:list")
    @ApiOperation(value = "显示全部")
    public R<MyPage<DeviceRecEntity>> list(@RequestParam(required = false) Long pageNum,
                                           @RequestParam(required = false) Long pageSize,
                                           @RequestParam(required = false, defaultValue = "rec_time") String sort,
                                           @RequestParam(required = false, defaultValue = "DESC") String order,
                                           @RequestParam(required = false) String imei,
                                           @RequestParam(required = false) Date startDate,
                                           @RequestParam(required = false) Date endDate) {
        MyPage<DeviceRecEntity> list = deviceRecService.listAll(pageNum, pageSize, sort, order, imei, startDate, endDate);
        return R.ok(list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("devicerec:info")
    public R info(@PathVariable("id") String id) {
        DeviceRecEntity deviceRec = deviceRecService.getById(id);

        return R.ok().put("deviceRec", deviceRec);
    }

    /**
     * 修改
     */
    @SysLog("修改录音")
    @RequestMapping("update")
    @RequiresPermissions("devicerec:update")
    public R update(@RequestBody DeviceRecEntity deviceRec) {
        deviceRecService.updateById(deviceRec);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除录音2")
    @DeleteMapping("delete2")
    @RequiresPermissions("devicerec:delete")
    @ApiOperation(value = "删除录音")
    public R delete2(@RequestBody String[] ids) {
        deviceRecService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @SysLog("删除录音")
    @DeleteMapping("delete/{id}")
    @RequiresPermissions("devicerec:delete")
    @ApiOperation(value = "删除录音")
    public R delete(@PathVariable String id) {
        deviceRecService.removeById(id);
        return R.ok();
    }

    @GetMapping("/test")
    @ApiOperation(value = "测试blob")
    public R test() {
        byte[] blob = BytesUtil.toStringHex("data.toString()");
        DeviceRecEntity deviceRecEntity = new DeviceRecEntity();
        String id = UIDGenerator.eventId();
        deviceRecEntity.setId(id);
        deviceRecEntity.setImei("11111111");
        deviceRecEntity.setRecTime(new Date());
        deviceRecEntity.setRecData(blob);
        deviceRecService.save(deviceRecEntity);

        return R.ok();
    }

    @SysLog("下载录音文件")
    @GetMapping(value = "downRec", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "下载录音文件")
    @RequiresPermissions("devicerec:download")
    public ResponseEntity<byte[]> downRec(HttpServletRequest request, String id) throws UnsupportedEncodingException {
        DeviceRecEntity deviceRecEntity = deviceRecService.getById(id);
        //文件名编码，解决乱码问题
        String fileName = deviceRecEntity.getImei() + "_" + deviceRecEntity.getRecTime() + ".mp3";
        //解决文件名乱码问题
        // String  fileName = URLEncoder.encode(file.getFilename(), StandardCharsets.UTF_8.toString());
        String userAgentString = request.getHeader("User-Agent");
//        String browser = UserAgent.parseUserAgentString(userAgentString).getBrowser().getGroup().getName();
//        if(browser.equals("Chrome") || browser.equals("Internet Exploer") || browser.equals("Safari")) {
            fileName = URLEncoder.encode(fileName,"utf-8").replaceAll("\\+", "%20");
//        } else {
//            fileName = MimeUtility.decodeText(fileName) ;  //encodeWord(fileName);
//        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(deviceRecEntity.getRecData().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(deviceRecEntity.getRecData());
    }

}
