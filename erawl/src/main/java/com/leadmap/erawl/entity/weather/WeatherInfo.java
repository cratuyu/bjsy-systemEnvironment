package com.leadmap.erawl.entity.weather;

import com.leadmap.erawl.entity.IdEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:当天天气信息类
 *
 * @author: yxm
 * @Date: 2018/9/11 10:59
 */
@Entity
@Table(name = "WeatherInfo")
public class WeatherInfo extends IdEntity {

    public WeatherInfo() {
        weatherInfoInHourList = new ArrayList<>();
    }

    @Column(name = "time")
    private Date time;

    @Transient
    private String strTime;

    /**
     * 城市
     */
    @Column(name = "city")
    private String city;

    /**
     * 城市编号
     */
    @Column(name = "cityId")
    private String cityId;

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
    @JoinTable(name = "WeatherInfo_WeatherInfoInHour", joinColumns = {@JoinColumn(name = "WeatherInfo_ID")},
            inverseJoinColumns = {@JoinColumn(name = "WeatherInfoInHour_ID")})
    private List<WeatherInfoInHour> weatherInfoInHourList;

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
    private List<WeatherInfoInDay> weatherInfo15DayList;

    @Transient
    private String weatherStatus;

    /**
     * 洗车指数级别
     */
    @Column(name = "carWashLevel")
    private String carWashLevel;

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
     * 减肥指数
     */
    @Column(name = "reduceWeight")
    private String reduceWeight;

    /**
     * 减肥指数级别
     */
    @Column(name = "reduceWeightLevel")
    private String reduceWeightLevel;

    /**
     * 血糖指数
     */
    @Column(name = "bloodSugar")
    private String bloodSugar;

    /**
     * 血糖指数级别
     */
    @Column(name = "bloodSugarLevel")
    private String bloodSugarLevel;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public List<WeatherInfoInHour> getWeatherInfoInHourList() {
        return weatherInfoInHourList;
    }

    public void setWeatherInfoInHourList(List<WeatherInfoInHour> weatherInfoInHourList) {
        this.weatherInfoInHourList = weatherInfoInHourList;
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

    public List<WeatherInfoInDay> getWeatherInfo15DayList() {
        return weatherInfo15DayList;
    }

    public void setWeatherInfo15DayList(List<WeatherInfoInDay> weatherInfo15DayList) {
        this.weatherInfo15DayList = weatherInfo15DayList;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public String getCarWashLevel() {
        return carWashLevel;
    }

    public void setCarWashLevel(String carWashLevel) {
        this.carWashLevel = carWashLevel;
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

    public String getReduceWeight() {
        return reduceWeight;
    }

    public void setReduceWeight(String reduceWeight) {
        this.reduceWeight = reduceWeight;
    }

    public String getReduceWeightLevel() {
        return reduceWeightLevel;
    }

    public void setReduceWeightLevel(String reduceWeightLevel) {
        this.reduceWeightLevel = reduceWeightLevel;
    }

    public String getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(String bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(String bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
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


}
