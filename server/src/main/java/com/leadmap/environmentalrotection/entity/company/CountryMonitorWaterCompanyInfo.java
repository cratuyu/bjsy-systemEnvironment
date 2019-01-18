package com.leadmap.environmentalrotection.entity.company;

import com.leadmap.environmentalrotection.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 * 水国控
 *
 * @author: ttq
 * @Date: 2018/7/10 10:12
 */
@Entity
@Table(name = "CountryMonitorWaterCompanyInfo")
public class CountryMonitorWaterCompanyInfo extends IdEntity implements Serializable {

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getMonitorDate() {
        return monitorDate;
    }

    public void setMonitorDate(String monitorDate) {
        this.monitorDate = monitorDate;
    }

    public String getDayCumulativeFlow() {
        return dayCumulativeFlow;
    }

    public void setDayCumulativeFlow(String dayCumulativeFlow) {
        this.dayCumulativeFlow = dayCumulativeFlow;
    }


    /**
     * 所属行政区
     */
    @Column(name = "cityName")
    private String cityName;

    /**
     * 企业名称
     */
    @Column(name = "companyName")
    private String companyName;

    /**
     * 企业地址
     */
    @Column(name = "companyAddress")
    private String companyAddress;

    /**
     * 监控点名称
     */
    @Column(name = "monitorName")
    private String monitorName;

    /**
     * 监测日期
     */
    @Column(name = "monitorDate")
    private String monitorDate;

    /**
     * 日累积流量(吨)
     */
    @Column(name = "dayCumulativeFlow")
    private String dayCumulativeFlow;
//    @Transient
//    private String dayCumulativeFlow48h;
//    @Transient
//    private String dayCumulativeFlow30d;

    /**
     * cod浓度日均值
     */
    @Column(name = "codDayMeanValue")
    private String codDayMeanValue;
//    @Transient
//    private String codDayMeanValue48h;
//    @Transient
//    private String codDayMeanValue30d;


    /**
     * cod排放标准
     */
    @Column(name = "codStandard")
    private String codStandard;

    /**
     * cod最近一次审核日期
     */
    @Column(name = "codLastAuditDate")
    private String codLastAuditDate;

    /**
     * cod合格情况
     */
    @Column(name = "codLastAuditStatus")
    private String codLastAuditStatus;

    /**
     * 备注
     */
    @Column(name = "codMark")
    private String codMark;

    public String getCodDayMeanValue() {
        return codDayMeanValue;
    }

    public void setCodDayMeanValue(String codDayMeanValue) {
        this.codDayMeanValue = codDayMeanValue;
    }

    public String getCodStandard() {
        return codStandard;
    }

    public void setCodStandard(String codStandard) {
        this.codStandard = codStandard;
    }

    public String getCodLastAuditDate() {
        return codLastAuditDate;
    }

    public void setCodLastAuditDate(String codLastAuditDate) {
        this.codLastAuditDate = codLastAuditDate;
    }

    public String getCodLastAuditStatus() {
        return codLastAuditStatus;
    }

    public void setCodLastAuditStatus(String codLastAuditStatus) {
        this.codLastAuditStatus = codLastAuditStatus;
    }

    public String getCodMark() {
        return codMark;
    }

    public void setCodMark(String codMark) {
        this.codMark = codMark;
    }

    public String getNh4DayMeanValue() {
        return nh4DayMeanValue;
    }

    public void setNh4DayMeanValue(String nh4DayMeanValue) {
        this.nh4DayMeanValue = nh4DayMeanValue;
    }

    public String getNh4Standard() {
        return nh4Standard;
    }

    public void setNh4Standard(String nh4Standard) {
        this.nh4Standard = nh4Standard;
    }

    public String getNh4LastAuditDate() {
        return nh4LastAuditDate;
    }

    public void setNh4LastAuditDate(String nh4LastAuditDate) {
        this.nh4LastAuditDate = nh4LastAuditDate;
    }

    public String getNh4LastAuditStatus() {
        return nh4LastAuditStatus;
    }

    public void setNh4LastAuditStatus(String nh4LastAuditStatus) {
        this.nh4LastAuditStatus = nh4LastAuditStatus;
    }

    public String getNh4Mark() {
        return nh4Mark;
    }

    public void setNh4Mark(String nh4Mark) {
        this.nh4Mark = nh4Mark;
    }

    /**
     * nh4浓度日均值
     */
    @Column(name = "nh4DayMeanValue")
    private String nh4DayMeanValue;
//    @Transient
//    private String nh4DayMeanValue48h;
//    @Transient
//    private String nh4DayMeanValue30d;



    /**
     * nh4排放标准
     */
    @Column(name = "nh4Standard")
    private String nh4Standard;

    /**
     * nh4最近一次审核日期
     */
    @Column(name = "nh4LastAuditDate")
    private String nh4LastAuditDate;

    /**
     * nh4合格情况
     */
    @Column(name = "nh4LastAuditStatus")
    private String nh4LastAuditStatus;

    /**
     * nh4备注
     */
    @Column(name = "nh4Mark")
    private String nh4Mark;

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
