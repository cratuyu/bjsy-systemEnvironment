package com.leadmap.environmentalrotection.dto.fairsense;

import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/17 17:44
 */
public class MonitorContainerInfo implements Serializable {
//    private int id;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(String recordTime) {
        RecordTime = recordTime;
    }

//    public WeatherDataInfo getWeather_data() {
//        return weather_data;
//    }
//
//    public void setWeather_data(WeatherDataInfo weather_data) {
//        this.weather_data = weather_data;
//    }

    public MonitorDataInfo getData() {
        return data;
    }

    public void setData(MonitorDataInfo data) {
        this.data = data;
    }

    private String RecordTime;
//    private WeatherDataInfo weather_data;
    private MonitorDataInfo data;


}
