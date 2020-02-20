package com.gps.api.modules.sys.controller;

import com.gps.common.model.Position;
import com.gps.common.reports.ReportUtils;
import com.gps.common.reports.model.StopReport;
import com.gps.common.reports.model.TripReport;
import com.gps.common.reports.model.TripsConfig;
import com.gps.db.utils.R;
import com.muheda.notice.entity.HPosition;
import com.muheda.notice.service.HPositionService;
import com.muheda.notice.utils.HbaseUtils;
import com.gps.api.common.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hbase")
@Api(tags = "Hbase接口测试")
public class HBaseController {

    @Autowired
    private HPositionService hPositionService;

    /**
     * @param pos
     * @Descripton: 保存
     * @Author: Sorin
     * @Date: 2018/3/22
     */
    @ApiOperation(value = "保存数据接口", notes = "保存数据接口")
    @PostMapping("/save")
    public R save(@RequestBody HPosition pos) {
        try {
            pos.setId(HbaseUtils.creatRowkey(pos.getId(), System.currentTimeMillis()));
            hPositionService.save(pos);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
//
//    /**
//     * @param imei
//     * @Descripton: 根据id查询
//     * @Author: Sorin
//     * @Date: 2018/3/22
//     */
//    @ApiOperation(value = "根据设备imei查询数据接口", notes = "根据imei查询数据接口")
//    @GetMapping("/get")
//    public R getBean(@RequestParam String imei, String startTime, String endTime) {
//        try {
//            Map<String, Collection<?>> data = new HashMap<>();
//
//            Date startDate = DateUtils.stringToDate(startTime, "yyyy-MM-dd HH:mm:ss");
//            Date endDate = DateUtils.stringToDate(endTime, "yyyy-MM-dd HH:mm:ss");
//            String startRow = HbaseUtils.creatRowkey(imei, startDate.getTime());
//            String endRow = HbaseUtils.creatRowkey(imei, endDate.getTime());
//
//            List<HPosition> hPositions = hPositionService.queryScan(new HPosition(), startRow, endRow);
//            boolean ignoreOdometer = true;
//
//            TripsConfig config = new TripsConfig(500, 300 * 1000,
//                    300 * 1000, 3600 * 1000,
//                    false, false,
//                    0.01);
//
//            Collection<Position> positions = new ArrayList<>();
//            hPositions.forEach(hPosition -> {
//                Position position = new Position();
//                position.setImei(imei);
//                position.setLatitude(hPosition.getLatitude());
//                position.setLongitude(hPosition.getLongtitude());
//                position.setCourse(hPosition.getDirection());
//                position.setSpeed(hPosition.getSpeed());
//                position.setTotalDistance(hPosition.getTotalMiles());
//                position.setTime(DateUtils.stringToDate(hPosition.getDeviceTime(), "yyyy-MM-dd HH:mm:ss"));
//                positions.add(position);
//            });
//            Collection<StopReport> stops = ReportUtils.detectTripsAndStops(imei, positions, config,
//                    ignoreOdometer, StopReport.class);
//
//            Collection<TripReport> trips = ReportUtils.detectTripsAndStops(imei, positions, config,
//                    ignoreOdometer, TripReport.class);
//
//            data.put("stops", stops);
//            data.put("trips", trips);
//            data.put("positions", hPositions);
//            return R.ok(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.error(e.getMessage());
//        }
//    }

//    @ApiOperation(value = "根据id查询数据接口", notes = "根据id查询数据接口")
//    @GetMapping("/queryScan")
//    public R queryScan(String id) {
//        try {
//            Map<String, Collection<?>> data = new HashMap<>();
//
//            Map<String, String> param = new HashMap<>();
//            param.put("deviceTime", ">10");
//            List<HPosition> hPositions = hPositionService.queryScan(new HPosition(), param);
//            boolean ignoreOdometer = true;
//
//            TripsConfig config = new TripsConfig(500, 300 * 1000,
//                    300 * 1000, 3600 * 1000,
//                    false, false,
//                    0.01);
//
//            Collection<Position> positions = new ArrayList<>();
//            hPositions.forEach(hPosition -> {
//                Position position = new Position();
//                BeanUtils.copyProperties(hPosition, position);
//                position.setImei(hPosition.getId());
//                positions.add(position);
//            });
//
//
//            Collection<StopReport> stops = ReportUtils.detectTripsAndStops(positions, config,
//                    ignoreOdometer, StopReport.class);
//
//            Collection<TripReport> trips = ReportUtils.detectTripsAndStops(hPositions, config,
//                    ignoreOdometer, TripReport.class);
//
//            data.put("stops", stops);
//            data.put("trips", trips);
//            data.put("positions", hPositions);
//            return R.ok(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.error(e.getMessage());
//        }
//    }

    @ApiOperation(value = "删除表", notes = "删除表")
    @DeleteMapping("/dropTable")
    public R dropTable() {
        try {
            hPositionService.dropTable(HPosition.class);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

}
