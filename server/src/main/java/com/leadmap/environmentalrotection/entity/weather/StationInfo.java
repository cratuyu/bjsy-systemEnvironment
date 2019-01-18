package com.leadmap.environmentalrotection.entity.weather;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/9 13:31
 */
@Entity
@Table(name = "StationInfo")
public class StationInfo implements Serializable {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @javax.persistence.Column(name = "stationNumber")
    private String stationNumber;
    @javax.persistence.Column(name = "stationName")
    private String stationName;

    public boolean isCountryStation() {
        return isCountryStation;
    }

    public void setCountryStation(boolean countryStation) {
        isCountryStation = countryStation;
    }

    @javax.persistence.Column(name = "isCountryStation")
    private boolean isCountryStation;

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

}
