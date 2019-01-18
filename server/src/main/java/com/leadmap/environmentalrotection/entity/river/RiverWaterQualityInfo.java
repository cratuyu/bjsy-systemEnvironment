package com.leadmap.environmentalrotection.entity.river;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 * 河流水质状况
 * @author: ttq
 * @Date: 2018/7/9 19:35
 */
@Entity
@Table(name = "RiverWaterQualityInfo")
public class RiverWaterQualityInfo implements Serializable {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRiverName() {
        return riverName;
    }

    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRiverSystem() {
        return riverSystem;
    }

    public void setRiverSystem(String riverSystem) {
        this.riverSystem = riverSystem;
    }

    public String getWaterQualityCategory() {
        return waterQualityCategory;
    }

    public void setWaterQualityCategory(String waterQualityCategory) {
        this.waterQualityCategory = waterQualityCategory;
    }

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 河流名
     */
    @javax.persistence.Column(name = "riverName")
    private String riverName;

    /**
     * 所在区县
     */
    @javax.persistence.Column(name = "cityName")
    private String cityName;

    /**
     * 水系
     */
    @javax.persistence.Column(name = "riverSystem")
    private String riverSystem;

    /**
     * 现状水质类别
     */
    @javax.persistence.Column(name = "waterQualityCategory")
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
    @javax.persistence.Column(name = "month")
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
