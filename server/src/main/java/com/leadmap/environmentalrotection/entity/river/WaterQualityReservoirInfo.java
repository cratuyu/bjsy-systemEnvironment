package com.leadmap.environmentalrotection.entity.river;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 * 河流水质状况
 *
 * @author: ttq
 * @Date: 2018/7/9 19:35
 */
@Entity
@Table(name = "WaterQualityReservoirInfo")
public class WaterQualityReservoirInfo implements Serializable {

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

    public String getWaterName() {
        return waterName;
    }

    public void setWaterName(String waterName) {
        this.waterName = waterName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWaterQualityCategory() {
        return waterQualityCategory;
    }

    public void setWaterQualityCategory(String waterQualityCategory) {
        this.waterQualityCategory = waterQualityCategory;
    }

    /**
     * 水库名
     */
    @Column(name = "waterName")
    private String waterName;

    /**
     * 所在区县
     */
    @Column(name = "cityName")
    private String cityName;

    /**
     * 现状水质类别
     */
    @Column(name = "waterQualityCategory")
    private String waterQualityCategory;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 月份
     */
    @Column(name = "month")
    private String month;

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

    @Column(name = "x")
    private String x;

    @Column(name = "y")
    private String y;
}
