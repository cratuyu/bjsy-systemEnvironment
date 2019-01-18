package com.leadmap.environmentalrotection.entity.weather;

import com.leadmap.environmentalrotection.entity.IdEntity;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 * 生活指数
 *
 * @author: ttq
 * @Date: 2018/7/5 13:31
 */
//@Entity
//@Table(name = "LifeIndex")
public class LifeIndex extends IdEntity implements Serializable {

    public String getUltravioletRays() {
        return ultravioletRays;
    }

    public void setUltravioletRays(String ultravioletRays) {
        this.ultravioletRays = ultravioletRays;
    }

    public String getDress() {
        return dress;
    }

    public void setDress(String dress) {
        this.dress = dress;
    }

    public String getCarWash() {
        return carWash;
    }

    public void setCarWash(String carWash) {
        this.carWash = carWash;
    }

    public String getAirPollutionDispersion() {
        return airPollutionDispersion;
    }

    public void setAirPollutionDispersion(String airPollutionDispersion) {
        this.airPollutionDispersion = airPollutionDispersion;
    }


    public String getUltravioletRaysLevel() {
        return ultravioletRaysLevel;
    }

    public void setUltravioletRaysLevel(String ultravioletRaysLevel) {
        this.ultravioletRaysLevel = ultravioletRaysLevel;
    }


    public String getDressLevel() {
        return dressLevel;
    }

    public void setDressLevel(String dressLevel) {
        this.dressLevel = dressLevel;
    }

    public String getCarWashLevel() {
        return carWashLevel;
    }

    public void setCarWashLevel(String carWashLevel) {
        this.carWashLevel = carWashLevel;
    }

    public String getAirPollutionDispersionLevel() {
        return airPollutionDispersionLevel;
    }

    public void setAirPollutionDispersionLevel(String airPollutionDispersionLevel) {
        this.airPollutionDispersionLevel = airPollutionDispersionLevel;
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

//    /**
//     * 减肥指数
//     */
//    @Column(name = "reduceWeight")
//    private String reduceWeight;
//
//    /**
//     * 减肥指数级别
//     */
//    @Column(name = "reduceWeightLevel")
//    private String reduceWeightLevel;

//    /**
//     * 血糖指数
//     */
//    @Column(name = "bloodSugar")
//    private String bloodSugar;
//
//    /**
//     * 血糖指数级别
//     */
//    @Column(name = "bloodSugarLevel")
//    private String bloodSugarLevel;

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
     * 运动指数
     */
    @Column(name = "motion")
    private String motion;

    /**
     * 运动指数级别
     */
    @Column(name = "motionLevel")
    private String motionLevel;


}
