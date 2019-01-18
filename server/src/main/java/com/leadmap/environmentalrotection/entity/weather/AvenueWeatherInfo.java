package com.leadmap.environmentalrotection.entity.weather;

import com.leadmap.environmentalrotection.entity.IdEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:   获取顺义区街道信息
 *
 * @author: yxm
 * @Date: 2018/10/18 9:53
 */
@Entity
@Table(name = "AvenueWeatherInfo")
public class AvenueWeatherInfo extends IdEntity implements Serializable {

    public AvenueWeatherInfo() {
        avenueWeatherInfoInHourList = new ArrayList<>();
    }

    @Column(name = "time")
    private Date time;

    @Transient
    private String strTime;

    /**
     * 街道
     */
    @Column(name = "avenue")
    private String avenue;

    /**
     * 街道编号
     */
    @Column(name = "avenueId")
    private String avenueId;

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
     * 当前温度
     */
    @Column(name = "currentTemperature")
    private String currentTemperature;

    /**
     * 相对湿度
     */
    @Column(name = "humidity")
    private String humidity;

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
     * 降水量
     */
    private String precipitation;


    @Transient
    private LifeIndex lifeIndex;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "AvenueWeatherInfo_AvenueWeatherInfoInHour", joinColumns = {@JoinColumn(name = "AvenueWeatherInfo_ID")},
            inverseJoinColumns = {@JoinColumn(name = "AvenueWeatherInfoInHour_ID")})
    private List<AvenueWeatherInfoInHour> avenueWeatherInfoInHourList;

    /**
     * AQI
     */
    @Column(name = "AQI")
    private String AQI;

    /**
     * 环境评价
     */
    @Column(name = "environmentalAssessment")
    private String environmentalAssessment;

    /**
     * 健康指引
     */
    @Column(name = "healthGuidelines")
    private String healthGuidelines;

    /**
     * 城市排名
     */
    @Column(name = "cityRankings")
    private String cityRankings;

    @Transient
    private List<AvenueWeatherInfoInDay> avenueWeatherInfo15DayList;

    @Transient
    private String weatherStatus;

    /**
     * 紫外线指数
     */
    @Column(name = "ultravioletRays")
    private String ultravioletRays;

    /**
     * 紫外线指数级别
     */
    @Column(name = "ultravioletRaysLevel")
    private String ultravioletRaysLevel;

    /**
     * 感冒
     */
    @Column(name = "cold")
    private String cold;

    /**
     * 感冒指数级别
     */
    @Column(name = "coldLevel")
    private String coldLevel;

    /**
     * 穿衣指数
     */
    @Column(name = "dress")
    private String dress;

    /**
     * 穿衣指数级别
     */
    @Column(name = "dressLevel")
    private String dressLevel;

    /**
     * 洗车指数
     */
    @Column(name = "carWash")
    private String carWash;

    /**
     * 洗车指数级别
     */
    @Column(name = "carWashLevel")
    private String carWashLevel;

    /**
     * 运动指数
     */
    @Column(name = "motion")
    private String motion;

    /**
     * 运动指数级别
     */
    @Column(name = "motionLevel")
    private String motionLevel;

    /**
     * 空气扩散指数
     */
    @Column(name = "airPollutionDispersion")
    private String airPollutionDispersion;

    /**
     * 空气扩散指数级别
     */
    @Column(name = "airPollutionDispersionLevel")
    private String airPollutionDispersionLevel;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getAvenue() {
        return avenue;
    }

    public void setAvenue(String avenue) {
        this.avenue = avenue;
    }

    public String getAvenueId() {
        return avenueId;
    }

    public void setAvenueId(String avenueId) {
        this.avenueId = avenueId;
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

    public String getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(String currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
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

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public LifeIndex getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(LifeIndex lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

    public List<AvenueWeatherInfoInHour> getAvenueWeatherInfoInHourList() {
        return avenueWeatherInfoInHourList;
    }

    public void setAvenueWeatherInfoInHourList(List<AvenueWeatherInfoInHour> avenueWeatherInfoInHourList) {
        this.avenueWeatherInfoInHourList = avenueWeatherInfoInHourList;
    }

    public String getAQI() {
        return AQI;
    }

    public void setAQI(String AQI) {
        this.AQI = AQI;
    }

    public String getEnvironmentalAssessment() {
        return environmentalAssessment;
    }

    public void setEnvironmentalAssessment(String environmentalAssessment) {
        this.environmentalAssessment = environmentalAssessment;
    }

    public String getHealthGuidelines() {
        return healthGuidelines;
    }

    public void setHealthGuidelines(String healthGuidelines) {
        this.healthGuidelines = healthGuidelines;
    }

    public String getCityRankings() {
        return cityRankings;
    }

    public void setCityRankings(String cityRankings) {
        this.cityRankings = cityRankings;
    }

    public List<AvenueWeatherInfoInDay> getAvenueWeatherInfo15DayList() {
        return avenueWeatherInfo15DayList;
    }

    public void setAvenueWeatherInfo15DayList(List<AvenueWeatherInfoInDay> avenueWeatherInfo15DayList) {
        this.avenueWeatherInfo15DayList = avenueWeatherInfo15DayList;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public String getUltravioletRays() {
        return ultravioletRays;
    }

    public void setUltravioletRays(String ultravioletRays) {
        this.ultravioletRays = ultravioletRays;
    }

    public String getUltravioletRaysLevel() {
        return ultravioletRaysLevel;
    }

    public void setUltravioletRaysLevel(String ultravioletRaysLevel) {
        this.ultravioletRaysLevel = ultravioletRaysLevel;
    }

    public String getCold() {
        return cold;
    }

    public void setCold(String cold) {
        this.cold = cold;
    }

    public String getColdLevel() {
        return coldLevel;
    }

    public void setColdLevel(String coldLevel) {
        this.coldLevel = coldLevel;
    }

    public String getDress() {
        return dress;
    }

    public void setDress(String dress) {
        this.dress = dress;
    }

    public String getDressLevel() {
        return dressLevel;
    }

    public void setDressLevel(String dressLevel) {
        this.dressLevel = dressLevel;
    }

    public String getCarWash() {
        return carWash;
    }

    public void setCarWash(String carWash) {
        this.carWash = carWash;
    }

    public String getCarWashLevel() {
        return carWashLevel;
    }

    public void setCarWashLevel(String carWashLevel) {
        this.carWashLevel = carWashLevel;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public String getMotionLevel() {
        return motionLevel;
    }

    public void setMotionLevel(String motionLevel) {
        this.motionLevel = motionLevel;
    }

    public String getAirPollutionDispersion() {
        return airPollutionDispersion;
    }

    public void setAirPollutionDispersion(String airPollutionDispersion) {
        this.airPollutionDispersion = airPollutionDispersion;
    }

    public String getAirPollutionDispersionLevel() {
        return airPollutionDispersionLevel;
    }

    public void setAirPollutionDispersionLevel(String airPollutionDispersionLevel) {
        this.airPollutionDispersionLevel = airPollutionDispersionLevel;
    }
}
