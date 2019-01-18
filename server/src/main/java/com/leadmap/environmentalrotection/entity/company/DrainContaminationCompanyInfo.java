package com.leadmap.environmentalrotection.entity.company;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/10 9:52
 */
@Entity
@Table(name = "DrainContaminationCompanyInfo")
public class DrainContaminationCompanyInfo implements Serializable {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 行政区划名称
     */
    @Column(name = "cityName")
    private String cityName;

    /**
     * 组织机构代码
     */
    @Column(name = "companyCode")
    private String companyCode;

    /**
     * 单位名称
     */
    @Column(name = "companyName")
    private String companyName;

    /**
     * 监测类别
     */
    @Column(name = "monitorType")
    private String monitorType;

    @Column(name = "x")
    private String x;

    @Column(name = "y")
    private String y;

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
}
