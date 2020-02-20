package com.gps.common.reports;

import com.gps.common.helper.MotionEventUtil;
import com.gps.common.model.DeviceState;
import com.gps.common.model.Event;
import com.gps.common.model.Position;
import com.gps.common.reports.model.BaseReport;
import com.gps.common.reports.model.StopReport;
import com.gps.common.reports.model.TripReport;
import com.gps.common.reports.model.TripsConfig;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public final class ReportUtils {

    public static void checkPeriodLimit(Date from, Date to) {
        long limit = 0;//Context.getConfig().getLong("report.periodLimit") * 1000;
        if (limit > 0 && to.getTime() - from.getTime() > limit) {
            throw new IllegalArgumentException("Time period exceeds the limit");
        }
    }
//
//    public static String getDistanceUnit(long userId) {
//        return (String) Context.getPermissionsManager().lookupAttribute(userId, "distanceUnit", "km");
//    }
//
//    public static String getSpeedUnit(long userId) {
//        return (String) Context.getPermissionsManager().lookupAttribute(userId, "speedUnit", "kn");
//    }
//
//    public static String getVolumeUnit(long userId) {
//        return (String) Context.getPermissionsManager().lookupAttribute(userId, "volumeUnit", "ltr");
//    }
//
//    public static TimeZone getTimezone(long userId) {
//        String timezone = (String) Context.getPermissionsManager().lookupAttribute(userId, "timezone", null);
//        return timezone != null ? TimeZone.getTimeZone(timezone) : TimeZone.getDefault();
//    }

    public static Collection<Long> getDeviceList(Collection<Long> deviceIds, Collection<Long> groupIds) {
        Collection<Long> result = new ArrayList<>();
        result.addAll(deviceIds);
        for (long groupId : groupIds) {
//            result.addAll(Context.getPermissionsManager().getGroupDevices(groupId));
        }
        return result;
    }

    public static double calculateDistance(Position firstPosition, Position lastPosition) {
        return calculateDistance(firstPosition, lastPosition, true);
    }

    public static double calculateDistance(Position firstPosition, Position lastPosition, boolean useOdometer) {
        double distance = 0.0;
        double firstOdometer = firstPosition.getDouble(Position.KEY_ODOMETER);
        double lastOdometer = lastPosition.getDouble(Position.KEY_ODOMETER);

        if (useOdometer && (firstOdometer != 0.0 || lastOdometer != 0.0)) {
            distance = lastOdometer - firstOdometer;
        } else if (firstPosition.getAttributes().containsKey(Position.KEY_TOTAL_DISTANCE)
                && lastPosition.getAttributes().containsKey(Position.KEY_TOTAL_DISTANCE)) {
            distance = lastPosition.getDouble(Position.KEY_TOTAL_DISTANCE)
                    - firstPosition.getDouble(Position.KEY_TOTAL_DISTANCE);
        } else {
            distance = (lastPosition.getTotalDistance() == null ? 0 : lastPosition.getTotalDistance()) -
                    (firstPosition.getTotalDistance() == null ? 0 : firstPosition.getTotalDistance());
        }
        return distance;
    }

    public static double calculateFuel(Position firstPosition, Position lastPosition) {

        if (firstPosition.getAttributes().get(Position.KEY_FUEL_LEVEL) != null
                && lastPosition.getAttributes().get(Position.KEY_FUEL_LEVEL) != null) {

            BigDecimal value = new BigDecimal(firstPosition.getDouble(Position.KEY_FUEL_LEVEL)
                    - lastPosition.getDouble(Position.KEY_FUEL_LEVEL));
            return value.setScale(1, RoundingMode.HALF_EVEN).doubleValue();
        }
        return 0;
    }

//    public static String findDriver(Position firstPosition, Position lastPosition) {
//        if (firstPosition.getAttributes().containsKey(Position.KEY_DRIVER_UNIQUE_ID)) {
//            return firstPosition.getString(Position.KEY_DRIVER_UNIQUE_ID);
//        } else if (lastPosition.getAttributes().containsKey(Position.KEY_DRIVER_UNIQUE_ID)) {
//            return lastPosition.getString(Position.KEY_DRIVER_UNIQUE_ID);
//        }
//        return null;
//    }

//    public static String findDriverName(String driverUniqueId) {
//        if (driverUniqueId != null && Context.getDriversManager() != null) {
//            Driver driver = Context.getDriversManager().getDriverByUniqueId(driverUniqueId);
//            if (driver != null) {
//                return driver.getName();
//            }
//        }
//        return null;
//    }

//    public static org.jxls.common.Context initializeContext(long userId) {
//        org.jxls.common.Context jxlsContext = PoiTransformer.createInitialContext();
//        jxlsContext.putVar("distanceUnit", getDistanceUnit(userId));
//        jxlsContext.putVar("speedUnit", getSpeedUnit(userId));
//        jxlsContext.putVar("volumeUnit", getVolumeUnit(userId));
//        jxlsContext.putVar("webUrl", Context.getVelocityEngine().getProperty("web.url"));
//        jxlsContext.putVar("dateTool", new DateTool());
//        jxlsContext.putVar("numberTool", new NumberTool());
//        jxlsContext.putVar("timezone", getTimezone(userId));
//        jxlsContext.putVar("locale", Locale.getDefault());
//        jxlsContext.putVar("bracketsRegex", "[\\{\\}\"]");
//        return jxlsContext;
//    }

//    public static void processTemplateWithSheets(
//            InputStream templateStream, OutputStream targetStream,
//            org.jxls.common.Context jxlsContext) throws IOException {
//
//        Transformer transformer = TransformerFactory.createTransformer(templateStream, targetStream);
//        List<Area> xlsAreas = new XlsCommentAreaBuilder(transformer).build();
//        for (Area xlsArea : xlsAreas) {
//            xlsArea.applyAt(new CellRef(xlsArea.getStartCellRef().getCellName()), jxlsContext);
//            xlsArea.setFormulaProcessor(new StandardFormulaProcessor());
//            xlsArea.processFormulas();
//        }
//        transformer.deleteSheet(xlsAreas.get(0).getStartCellRef().getSheetName());
//        transformer.write();
//    }

    private static TripReport calculateTrip(ArrayList<Position> positions, int startIndex, int endIndex, boolean ignoreOdometer) {
        Position startTrip = positions.get(startIndex);
        Position endTrip = positions.get(endIndex);

        double speedMax = 0.0;
        double speedSum = 0.0;
        for (int i = startIndex; i <= endIndex; i++) {
            double speed = positions.get(i).getSpeed();
            speedSum += speed;
            if (speed > speedMax) {
                speedMax = speed;
            }
        }

        TripReport trip = new TripReport();

        long tripDuration = endTrip.getFixTime().getTime() - startTrip.getFixTime().getTime();
        String deviceId = startTrip.getImei();
        trip.setDeviceId(deviceId);
//        trip.setDeviceName(Context.getIdentityManager().getById(deviceId).getName());

//        trip.setStartPositionId(startTrip.getId());
        trip.setStartLat(startTrip.getLatitude());
        trip.setStartLon(startTrip.getLongitude());
        trip.setStartTime(startTrip.getFixTime());
        trip.setStartDistance(startTrip.getTotalDistance());
//        String startAddress = startTrip.getAddress();
//        if (startAddress == null && Context.getGeocoder() != null
//                && Context.getConfig().getBoolean("geocoder.onRequest")) {
//            startAddress = Context.getGeocoder().getAddress(startTrip.getLatitude(), startTrip.getLongitude(), null);
//        }
//        trip.setStartAddress(startAddress);

//        trip.setEndPositionId(endTrip.getId());
        trip.setEndLat(endTrip.getLatitude());
        trip.setEndLon(endTrip.getLongitude());
        trip.setEndTime(endTrip.getFixTime());
        trip.setEndDistance(endTrip.getTotalDistance());
//        String endAddress = endTrip.getAddress();
//        if (endAddress == null && Context.getGeocoder() != null
//                && Context.getConfig().getBoolean("geocoder.onRequest")) {
//            endAddress = Context.getGeocoder().getAddress(endTrip.getLatitude(), endTrip.getLongitude(), null);
//        }
//        trip.setEndAddress(endAddress);

        trip.setDistance(calculateDistance(startTrip, endTrip, !ignoreOdometer));
        trip.setDuration(tripDuration);
        trip.setAverageSpeed(speedSum / (endIndex - startIndex));
        trip.setMaxSpeed(speedMax);
        trip.setSpentFuel(calculateFuel(startTrip, endTrip));

//        trip.setDriverUniqueId(findDriver(startTrip, endTrip));
//        trip.setDriverName(findDriverName(trip.getDriverUniqueId()));

        if (!ignoreOdometer
                && startTrip.getDouble(Position.KEY_ODOMETER) != 0
                && endTrip.getDouble(Position.KEY_ODOMETER) != 0) {
            trip.setStartOdometer(startTrip.getDouble(Position.KEY_ODOMETER));
            trip.setEndOdometer(endTrip.getDouble(Position.KEY_ODOMETER));
        } else {
            trip.setStartOdometer(startTrip.getDouble(Position.KEY_TOTAL_DISTANCE));
            trip.setEndOdometer(endTrip.getDouble(Position.KEY_TOTAL_DISTANCE));
        }
        return trip;
    }

    private static StopReport calculateStop(ArrayList<Position> positions, int startIndex, int endIndex, boolean ignoreOdometer) {
        Position startStop = positions.get(startIndex);
        Position endStop = positions.get(endIndex);

        StopReport stop = new StopReport();

        String deviceId = startStop.getImei();
        stop.setDeviceId(deviceId);
//        stop.setDeviceName(Context.getIdentityManager().getById(deviceId).getName());

//        stop.setPositionId(startStop.getId());
        stop.setLatitude(startStop.getLatitude());
        stop.setLongitude(startStop.getLongitude());
        stop.setStartTime(startStop.getFixTime());
//        String address = startStop.getAddress();
//        if (address == null && Context.getGeocoder() != null
//                && Context.getConfig().getBoolean("geocoder.onRequest")) {
//            address = Context.getGeocoder().getAddress(stop.getLatitude(), stop.getLongitude(), null);
//        }
//        stop.setAddress(address);

        stop.setEndTime(endStop.getFixTime());

        long stopDuration = endStop.getFixTime().getTime() - startStop.getFixTime().getTime();
        stop.setDuration(stopDuration);
        stop.setSpentFuel(calculateFuel(startStop, endStop));

        long engineHours = 0;
        if (startStop.getAttributes().containsKey(Position.KEY_HOURS)
                && endStop.getAttributes().containsKey(Position.KEY_HOURS)) {
            engineHours = endStop.getLong(Position.KEY_HOURS) - startStop.getLong(Position.KEY_HOURS);
        } else if (/*Context.getConfig().getBoolean("processing.engineHours.enable")*/true) {
            // Temporary fallback for old data, to be removed in May 2019
            for (int i = startIndex + 1; i <= endIndex; i++) {
                Position posi = positions.get(i);
                Position posi_1 = positions.get(i-1);
                if (posi.getBoolean(Position.KEY_IGNITION) && posi_1.getBoolean(Position.KEY_IGNITION)) {
                    engineHours += posi.getFixTime().getTime() - posi_1.getFixTime().getTime();
                }
            }
        }
        stop.setEngineHours(engineHours);

        if (!ignoreOdometer
                && startStop.getDouble(Position.KEY_ODOMETER) != 0
                && endStop.getDouble(Position.KEY_ODOMETER) != 0) {
            stop.setStartOdometer(startStop.getDouble(Position.KEY_ODOMETER));
            stop.setEndOdometer(endStop.getDouble(Position.KEY_ODOMETER));
        } else {
            stop.setStartOdometer(startStop.getDouble(Position.KEY_TOTAL_DISTANCE));
            stop.setEndOdometer(endStop.getDouble(Position.KEY_TOTAL_DISTANCE));
        }
        return stop;
    }

    private static <T extends BaseReport> T calculateTripOrStop(
            ArrayList<Position> positions, int startIndex, int endIndex, boolean ignoreOdometer, Class<T> reportClass) {

        if (reportClass.equals(TripReport.class)) {
            return (T) calculateTrip(positions, startIndex, endIndex, ignoreOdometer);
        } else {
            return (T) calculateStop(positions, startIndex, endIndex, ignoreOdometer);
        }
    }

    private static boolean isMoving(ArrayList<Position> positions, int index, TripsConfig tripsConfig) {
        Position pos = positions.get(index);
        long minimalNoDataDuration = tripsConfig.getMinimalNoDataDuration();
        if (minimalNoDataDuration > 0) {
            boolean afterGap = index < positions.size() - 1
                    && positions.get(index + 1).getFixTime().getTime() - pos.getFixTime().getTime() >= minimalNoDataDuration;
            boolean beforeGap = index > 0
                    && pos.getFixTime().getTime() - positions.get(index - 1).getFixTime().getTime() >= minimalNoDataDuration;
            if (beforeGap || afterGap) {
                return false;
            }
        }
        if (pos.getAttributes().containsKey(Position.KEY_MOTION) && pos.getAttributes().get(Position.KEY_MOTION) instanceof Boolean) {
            return pos.getBoolean(Position.KEY_MOTION);
        }
        return pos.getSpeed() > tripsConfig.getSpeedThreshold();
    }

    public static <T extends BaseReport> Collection<T> detectTripsAndStops(String imei, Collection<Position> positionCollection,
            TripsConfig tripsConfig, boolean ignoreOdometer, Class<T> reportClass) {

        Collection<T> result = new ArrayList<>();

        ArrayList<Position> positions = new ArrayList<>(positionCollection);
        if (!positions.isEmpty()) {
            boolean trips = reportClass.equals(TripReport.class);
            DeviceState deviceState = new DeviceState();
            deviceState.setMotionState(isMoving(positions, 0, tripsConfig));
            deviceState.setMotionPosition(positions.get(0));

            int startEventIndex = trips == deviceState.getMotionState() ? 0 : -1;
            int startNoEventIndex = -1;
            for (int i = 0; i < positions.size(); i++) {
                boolean isM = isMoving(positions, i, tripsConfig);
                Position po = positions.get(i);
                Map<Event, Position> event = MotionEventUtil.updateMotionState(imei, tripsConfig, deviceState, po, isM);

                boolean isMov = deviceState.getMotionState();
                Position motPos = deviceState.getMotionPosition();
                if (startEventIndex == -1
                        && (trips != isMov && motPos != null || trips == isMov && event != null)) {
                    startEventIndex = i;
                    startNoEventIndex = -1;
                } else if (trips != isMov && startEventIndex != -1 && motPos == null && event == null) {
                    startEventIndex = -1;
                }
                if (startNoEventIndex == -1
                        && (trips == isMov && motPos != null || trips != isMov && event != null)) {
                    startNoEventIndex = i;
                } else if (startNoEventIndex != -1 && motPos == null && event == null) {
                    startNoEventIndex = -1;
                }
                if (startEventIndex != -1 && startNoEventIndex != -1 && event != null && trips != isMov) {
                    T res = calculateTripOrStop(positions, startEventIndex, startNoEventIndex, ignoreOdometer, reportClass);
                    if (checkInvalid(res, trips, tripsConfig.getMinimalParkTime())) {
                        result.add(res);
                    }
                    startEventIndex = -1;
                }
            }
            if (startEventIndex != -1 && (startNoEventIndex != -1 || !trips)) {
                T res = calculateTripOrStop(positions, startEventIndex,
                        startNoEventIndex != -1 ? startNoEventIndex : positions.size() - 1,
                        ignoreOdometer, reportClass);
                if (checkInvalid(res, trips, tripsConfig.getMinimalParkTime())) {
                    result.add(res);
                }
            }
        }

        return result;
    }

    private static <T extends BaseReport> boolean checkInvalid(T res, boolean trips, long minimalParkTime) {
        if (trips) {
            return true;
        }
        if (res.getDuration() < minimalParkTime) {
            return false;
        }
        return true;
    }

}
