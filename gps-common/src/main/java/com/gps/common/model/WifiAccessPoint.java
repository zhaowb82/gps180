package com.gps.common.model;

//import com.fasterxml.jackson.annotation.JsonInclude;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class WifiAccessPoint {

    public static WifiAccessPoint from(String macAddress, int signalStrength) {
        WifiAccessPoint wifiAccessPoint = new WifiAccessPoint();
        wifiAccessPoint.setMacAddress(macAddress);
        wifiAccessPoint.setSignalStrength(signalStrength);
        return wifiAccessPoint;
    }

    public static WifiAccessPoint from(String macAddress, int signalStrength, int channel) {
        WifiAccessPoint wifiAccessPoint = from(macAddress, signalStrength);
        wifiAccessPoint.setChannel(channel);
        return wifiAccessPoint;
    }

    private String macAddress;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    private Integer signalStrength;

    public Integer getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Integer signalStrength) {
        this.signalStrength = signalStrength;
    }

    private Integer channel;

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

}
