package com.leadmap.erawl.entity.weather;

import javax.persistence.*;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/7 11:30
 */
@Entity
@Table(name = "StationInfo")
public class StationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stationNumber")
    private String stationNumber;

    @Column(name = "stationName")
    private String stationName;

    @Column(name = "isCountryStation")
    private boolean isCountryStation;

    @Column(name = "x")
    private String x;

    @Column(name = "y")
    private String y;

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

    public boolean isCountryStation() {
        return isCountryStation;
    }

    public void setCountryStation(boolean countryStation) {
        isCountryStation = countryStation;
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
