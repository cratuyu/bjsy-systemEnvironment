package com.leadmap.erawl.entity.weather;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/11 11:28
 */
@Entity
@Table(name = "WeatherCityInfo")
public class WeatherCityInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 气象行政区编号
     */
    @NotBlank
    @Column(name = "cityId")
    private String cityId;

    /**
     * 气象行政区名称
     */
    @NotBlank
    @Column(name = "cityName")
    private String cityName;

    /**
     * 监测点
     */
    @Column(name = "monitoringSite")
    private String monitoringSite;

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

    public String getMonitoringSite() {
        return monitoringSite;
    }

    public void setMonitoringSite(String monitoringSite) {
        this.monitoringSite = monitoringSite;
    }
}
