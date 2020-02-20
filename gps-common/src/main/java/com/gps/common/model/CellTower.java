package com.gps.common.model;

//import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CellTower implements Serializable {

    public static CellTower from(int mcc, int mnc, int lac, long cid) {
        CellTower cellTower = new CellTower();
        cellTower.setMobileCountryCode(mcc);
        cellTower.setMobileNetworkCode(mnc);
        cellTower.setLocationAreaCode(lac);
        cellTower.setCellId(cid);
        return cellTower;
    }

    public static CellTower from(int mcc, int mnc, int lac, long cid, int rssi) {
        CellTower cellTower = CellTower.from(mcc, mnc, lac, cid);
        cellTower.setSignalStrength(rssi);
        return cellTower;
    }

    public static CellTower fromLacCid(int lac, long cid) {
        return null;//from(
//                Context.getConfig().getInteger("geolocation.mcc"),
//                Context.getConfig().getInteger("geolocation.mnc"), lac, cid);
    }

    public static CellTower fromCidLac(long cid, int lac) {
        return fromLacCid(lac, cid);
    }

    private String radioType;

    public String getRadioType() {
        return radioType;
    }

    public void setRadioType(String radioType) {
        this.radioType = radioType;
    }

    private Long cellId;

    public Long getCellId() {
        return cellId;
    }

    public void setCellId(Long cellId) {
        this.cellId = cellId;
    }

    private Integer locationAreaCode;

    public Integer getLocationAreaCode() {
        return locationAreaCode;
    }

    public void setLocationAreaCode(Integer locationAreaCode) {
        this.locationAreaCode = locationAreaCode;
    }

    private Integer mobileCountryCode;

    public Integer getMobileCountryCode() {
        return mobileCountryCode;
    }

    public void setMobileCountryCode(Integer mobileCountryCode) {
        this.mobileCountryCode = mobileCountryCode;
    }

    private Integer mobileNetworkCode;

    public Integer getMobileNetworkCode() {
        return mobileNetworkCode;
    }

    public void setMobileNetworkCode(Integer mobileNetworkCode) {
        this.mobileNetworkCode = mobileNetworkCode;
    }

    private Integer signalStrength;

    public Integer getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Integer signalStrength) {
        this.signalStrength = signalStrength;
    }

    public void setOperator(long operator) {
        String operatorString = String.valueOf(operator);
        mobileCountryCode = Integer.parseInt(operatorString.substring(0, 3));
        mobileNetworkCode = Integer.parseInt(operatorString.substring(3));
    }

}
