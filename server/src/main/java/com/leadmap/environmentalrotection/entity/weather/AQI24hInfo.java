package com.leadmap.environmentalrotection.entity.weather;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/9 11:02
 */
@Entity
@Table(name = "AQI24hInfo")
public class AQI24hInfo implements Serializable {
    public AQI24hInfo() {
        this.createTime = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyId;

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public boolean isCountryStation() {
        return isCountryStation;
    }

    public void setCountryStation(boolean countryStation) {
        isCountryStation = countryStation;
    }

    private String stationName;

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getAqi24h() {
        return aqi24h;
    }

    public void setAqi24h(String aqi24h) {
        this.aqi24h = aqi24h;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    private String stationNumber;
    private boolean isCountryStation;

    private String aqi24h;
    private String dateTime;

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

    @javax.persistence.Column(name = "x")
    private String x;

    @javax.persistence.Column(name = "y")
    private String y;

    @javax.persistence.Column(name = "createTime")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
