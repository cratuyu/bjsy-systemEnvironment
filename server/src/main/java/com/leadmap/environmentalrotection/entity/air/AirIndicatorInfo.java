package com.leadmap.environmentalrotection.entity.air;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 * 指标信息
 *
 * @author: ttq
 * @Date: 2018/7/12 20:11
 */
@Entity
@Table(name = "AirIndicatorInfo")
public class AirIndicatorInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 指标名
     */
    @Column(name = "name")
    private String name;

    /**
     * 下限
     */
    @Column(name = "lowerValue")
    private double lowerValue;

    /**
     * 上限
     */
    @Column(name = "upperValue")
    private double upperValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLowerValue() {
        return lowerValue;
    }

    public void setLowerValue(double lowerValue) {
        this.lowerValue = lowerValue;
    }

    public double getUpperValue() {
        return upperValue;
    }

    public void setUpperValue(double upperValue) {
        this.upperValue = upperValue;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * 颜色
     */
    @Column(name = "color")
    private String color;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    private String level;

    private String grade;

    private String tip;
}
