/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 * <p>
 * https://www.gps180.com
 * <p>
 * 版权所有，侵权必究！
 */

package com.gps.api.modules.sys.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gps.api.common.annotation.SysLog;
import com.gps.common.helper.UnitsConverter;
import com.gps.common.reports.model.TripReport;
import com.gps.db.entity.DeviceStatusEntity;
import com.gps.db.entity.PositionsEntity;
import com.gps.db.service.DeviceStatusService;
import com.gps.db.service.PositionsService;
import com.gps.db.utils.R;
import com.gps.api.common.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gps.common.model.Position;
import com.gps.common.reports.ReportUtils;
import com.gps.common.reports.model.StopReport;
import com.gps.common.reports.model.TripsConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("gps")
@Api(tags = "GPS轨迹，统计等其它")
@Slf4j
public class GpsController extends AbstractController {

    @Autowired
    private DeviceStatusService deviceStatusService;
    @Autowired
    private PositionsService positionsService;

    @GetMapping(value = "lastPosition")
    @ApiOperation(value = "")
    public R<List<DeviceStatusEntity>> lastPosition(@RequestParam(required = false) List<String> deviceids, long lastQueryPositionTime) {
//		if (deviceids == null || deviceids.size() == 0) {
//			List<DeviceStatusEntity> list = deviceStatusService.list(Wrappers.<DeviceStatusEntity>query()
//					.select(DeviceStatusEntity.class, info -> !info.getColumn().equals("org_msg") && !info.getColumn().equals("attribute")));
//			return R.ok(list);
//		}
//		List<DeviceStatusEntity> list = deviceStatusService.list(Wrappers.<DeviceStatusEntity>query()
//				.select(DeviceStatusEntity.class, info -> !info.getColumn().equals("org_msg") && !info.getColumn().equals("attribute"))
//				.lambda().in(DeviceStatusEntity::getImei, deviceids));
        List<DeviceStatusEntity> list = deviceStatusService.selectByImei(deviceids);
        return R.ok(list);
    }

    @RequestMapping("/postion/{id}")
    public R<DeviceStatusEntity> postion(@PathVariable("id") String id) {
        DeviceStatusEntity userDevice = deviceStatusService.getDeviceWithCache(id);
        return R.ok(userDevice);
    }

//	@GetMapping(value = "trackPos1")
//	@ApiOperation(value = "查询轨迹")
//    @Deprecated
//	public R trackPos1(String imei, int lbs, String timeorder, Date begintime, Date endtime) {
//		List<PositionsEntity> list = positionsService.list(Wrappers.<PositionsEntity>query()
//				.lambda()
//				.eq(PositionsEntity::getImei, imei)
//				.between(PositionsEntity::getDeviceTime, begintime, endtime)
//				.orderByAsc(PositionsEntity::getDeviceTime));
//		return R.ok(list);
//	}

    @SysLog("查询轨迹,停车点,行程")
    @GetMapping(value = "trackPos")
    @ApiOperation(value = "查询轨迹,停车点,行程")
    public R<Map<String, Collection<?>>> trackPos(String imei, Date startTime, Date endTime,
                      @RequestParam(required = false, defaultValue = "1") Long minParkDur,
                      @RequestParam(required = false, defaultValue = "1") Long minTripDur,
                      @RequestParam(required = false, defaultValue = "10") Long minParkTime,
                      @RequestParam(required = false, defaultValue = "true") Boolean withPos,
                      @RequestParam(required = false, defaultValue = "false") Boolean withStop,
                      @RequestParam(required = false, defaultValue = "false") Boolean withTrip) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endTime);
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            endTime = calendar.getTime();

			Map<String, Collection<?>> data = new HashMap<>();
			Collection<Position> positionsr = null;
				List<PositionsEntity> list = positionsService.list(Wrappers.<PositionsEntity>query()
						.lambda()
						.eq(PositionsEntity::getImei, imei)
						.between(PositionsEntity::getDeviceTime, startTime, endTime)
						.orderByAsc(PositionsEntity::getDeviceTime));
				Collection<Position> positions = new ArrayList<>();
				list.forEach(positionsEntity -> {
					Position position = new Position();
                    BeanUtils.copyProperties(positionsEntity, position);

					position.setImei(imei);
					position.setLatitude(positionsEntity.getLatitude());
					position.setLongitude(positionsEntity.getLongitude());
                    position.setSpeed(positionsEntity.getSpeed());

					position.setCourse(positionsEntity.getDirection());
					position.setTotalDistance(positionsEntity.getTotalDistance());
					position.setTime(positionsEntity.getDeviceTime());
					positions.add(position);
				});
				positionsr = positions;
            if (withPos) {
                data.put("positions", positionsr);
            }
            double sp = UnitsConverter.knotsFromKph(5);// kmp
            TripsConfig config = new TripsConfig(300, 300 * 1000,
                    120 * 1000, 3600 * 1000,
                    false, false, sp);
            if (minParkDur != null) {
                config.setMinimalParkingDuration(minParkDur * 60 * 1000);
            }
            if (minTripDur != null) {
                config.setMinimalTripDuration(minTripDur * 60 * 1000);
            }
            if (minParkTime != null) {
                config.setMinimalParkTime(minParkTime * 60 * 1000);
            }
            if (withStop) {
//                TripsConfig config = new TripsConfig();
//                config.setMinimalTripDistance(500);
//                config.setMinimalTripDuration(5 * 60 * 1000);
//                config.setMinimalParkingDuration(2 * 60 * 1000);
//                config.setMinimalNoDataDuration(60*60*1000);
//                config.setUseIgnition(true);
//                config.setSpeedThreshold(10);
//                config.setProcessInvalidPositions(false);
                Collection<StopReport> stops = ReportUtils.detectTripsAndStops(imei, positionsr, config,
                        true, StopReport.class);
                data.put("stops", stops);
            }
            if (withTrip) {
                Collection<TripReport> trips = ReportUtils.detectTripsAndStops(imei, positionsr, config,
                        true, TripReport.class);
                data.put("trips", trips);
            }
            return R.ok(data);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
}
