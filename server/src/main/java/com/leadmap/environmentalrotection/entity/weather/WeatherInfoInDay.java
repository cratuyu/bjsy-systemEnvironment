package com.leadmap.environmentalrotection.entity.weather;

import com.leadmap.environmentalrotection.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/5 15:15
 */
@Entity
@Table(name = "WeatherInfoInDay")
public class WeatherInfoInDay extends IdEntity implements Serializable {

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
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

    /**
     * 城市id
     */
    @Column(name = "cityId")
    private String cityId;

    /**
     * 时间
     */
    @Column(name = "day")
    private String day;

    /**
     * 最高温度
     */
    @Column(name = "maxTemperature")
    private String maxTemperature;

    /**
     * 最低温度
     */
    @Column(name = "minTemperature")
    private String minTemperature;

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

    /**
     * 天气状况
     */
    @Column(name="weatherStatus")
    private String weatherStatus;

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

}
