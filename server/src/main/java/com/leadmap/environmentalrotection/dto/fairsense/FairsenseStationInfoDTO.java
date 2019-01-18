package com.leadmap.environmentalrotection.dto.fairsense;

import com.leadmap.environmentalrotection.entity.air.AirQualityIndex;

import java.io.Serializable;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/17 17:23
 */
public class FairsenseStationInfoDTO implements Serializable {
//    public int getId() {
//        return stationId;
//    }
//
//    public void setId(int id) {
//        this.stationId = id;
//    }
//
//    public GeoInfoDTO getGeo() {
//        return geo;
//    }
//
//    public void setGeo(GeoInfoDTO geo) {
//        this.geo = geo;
//    }
//
//    public List<String> getParams() {
//        return params;
//    }
//
//    public void setParams(List<String> params) {
//        this.params = params;
//    }

    //    private GeoInfoDTO geo;
//    private List<String> params;



    private int stationId;

    private String x;

    private String y;

    private String stationName;

    private String station_type;

    private List<AirQualityIndex> airQualityIndexList;

//    private List<MonitorContainerInfo> realTimeMonitorDataInfosList;



    public List<AirQualityIndex> getAirQualityIndexList() {
        return airQualityIndexList;
    }

    public void setAirQualityIndexList(List<AirQualityIndex> airQualityIndexList) {
        this.airQualityIndexList = airQualityIndexList;
    }

    //    private List<MonitorContainerInfo> historyMonitorDataInfosList;

//    public List<MonitorContainerInfo> getHistoryMonitorDataInfosList() {
//        return historyMonitorDataInfosList;
//    }
//
//    public void setHistoryMonitorDataInfosList(List<MonitorContainerInfo> historyMonitorDataInfosList) {
//        this.historyMonitorDataInfosList = historyMonitorDataInfosList;
//    }


    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStation_type() {
        return station_type;
    }

    public void setStation_type(String station_type) {
        this.station_type = station_type;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }


}
