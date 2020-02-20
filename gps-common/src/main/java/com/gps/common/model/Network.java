package com.gps.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class Network implements Serializable {

    public Network() {
    }

    public Network(CellTower cellTower) {
        addCellTower(cellTower);
    }

    private Integer homeMobileCountryCode;
    private Integer homeMobileNetworkCode;
    private String radioType = "gsm";
    private String carrier;
    private Boolean considerIp = false;
    private Collection<CellTower> cellTowers;
    public void addCellTower(CellTower cellTower) {
        if (cellTowers == null) {
            cellTowers = new ArrayList<>();
        }
        cellTowers.add(cellTower);
    }

    private Collection<WifiAccessPoint> wifiAccessPoints;

    public void addWifiAccessPoint(WifiAccessPoint wifiAccessPoint) {
        if (wifiAccessPoints == null) {
            wifiAccessPoints = new ArrayList<>();
        }
        wifiAccessPoints.add(wifiAccessPoint);
    }

}
