package com.leadmap.environmentalrotection.entity.weather;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 * 气象行政区信息
 *
 * @author: ttq
 * @Date: 2018/7/4 14:11
 */
@Entity
@Table(name = "WeatherCityInfo")
public class WeatherCityInfo implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * 气象行政区编号
     */
    @NotBlank
    @javax.persistence.Column(name = "cityId")
    private String cityId;

    /**
     * 气象行政区名称
     */
    @NotBlank
    @javax.persistence.Column(name = "cityName")
    private String cityName;

    /**
     * 监测点
     */
    @javax.persistence.Column(name = "monitoringSite")
    private String monitoringSite;

    @Column(name = "x")
    private String x;

    @Column(name = "y")
    private String y;

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

    public String getMonitoringSite() {
        return monitoringSite;
    }

    public void setMonitoringSite(String monitoringSite) {
        this.monitoringSite = monitoringSite;
    }
}
