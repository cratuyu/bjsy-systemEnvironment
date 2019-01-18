package com.leadmap.erawl.entity.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leadmap.erawl.entity.IdEntity;

import javax.persistence.*;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/11 11:10
 */
@Entity
@Table(name = "WeatherInfoInHour")
public class WeatherInfoInHour extends IdEntity {

    /**
     * 时间
     */
    @Column(name = "hour")
    private String hour;

    /**
     * 当前温度
     */
    @Column(name = "temperature")
    private String temperature;

    /**
     * 风向
     */
    @Column(name = "windDirection")
    private String windDirection;

    /**
     * 风力强度
     */
    @Column(name = "windPower")
    private String windPower;

    @ManyToOne
    @JoinColumn(name = "WeatherInfo_ID")
    @JsonIgnore
    private WeatherInfo weatherInfo;

    /**
     * 天气状况
     */
    @Column(name = "weatherStatus")
    private String weatherStatus;

    /**
     * 天气状况图片url
     */
    @Column(name = "weatherImgUrl")
    @JsonIgnore
    private String weatherImgUrl;

    @Transient
    private String weatherStatusImgUrl;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindPower() {
        return windPower;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public String getWeatherImgUrl() {
        return weatherImgUrl;
    }

    public void setWeatherImgUrl(String weatherImgUrl) {
        this.weatherImgUrl = weatherImgUrl;
    }

    public String getWeatherStatusImgUrl() {
        return weatherStatusImgUrl;
    }

    public void setWeatherStatusImgUrl(String weatherStatusImgUrl) {
        this.weatherStatusImgUrl = weatherStatusImgUrl;
    }
}
