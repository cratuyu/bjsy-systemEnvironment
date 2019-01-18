package com.leadmap.erawl.entity.weather;

import com.leadmap.erawl.entity.IdEntity;

import javax.persistence.Column;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/11 11:07
 */
public class LifeIndex extends IdEntity {

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

    /**
     * 洗车指数级别
     */
    @Column(name = "carWashLevel")
    private String carWashLevel;

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

    public String getCarWashLevel() {
        return carWashLevel;
    }

    public void setCarWashLevel(String carWashLevel) {
        this.carWashLevel = carWashLevel;
    }
}
