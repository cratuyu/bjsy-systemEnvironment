package com.leadmap.erawl.entity.company;

import com.leadmap.erawl.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/11 10:17
 */
@Entity
@Table(name = "CountryMonitorAirCompanyInfo")
public class CountryMonitorAirCompanyInfo extends IdEntity {

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
     * 日累积流量(立方米)
     */
    @Column(name = "dayCumulativeFlow")
    private String dayCumulativeFlow;

    @Transient
    private String dayCumulativeFlow48h;

    @Transient
    private String dayCumulativeFlow30d;

    /**
     * 烟气流速
     * (米/秒)
     */
    @Column(name = "gasFlow")
    private String gasFlow;

    private String gasFlow48h;

    private String gasFlow30d;

    /**
     * no浓度日均值
     */
    @Column(name = "noDayMeanValue")
    private String noDayMeanValue;

    @Transient
    private String noDayMeanValue48h;

    @Transient
    private String noDayMeanValue30d;

    /**
     * nh4排放标准
     */
    @Column(name = "noStandard")
    private String noStandard;

    /**
     * no最近一次审核日期
     */
    @Column(name = "noLastAuditDate")
    private String noLastAuditDate;

    /**
     * no合格情况
     */
    @Column(name = "noLastAuditStatus")
    private String noLastAuditStatus;

    /**
     * no备注
     */
    @Column(name = "noMark")
    private String noMark;

    /**
     * so2浓度日均值
     */
    @Column(name = "so2DayMeanValue")
    private String so2DayMeanValue;

    @Transient
    private String so2DayMeanValue48h;

    @Transient
    private String so2DayMeanValue30d;

    /**
     * so2排放标准
     */
    @Column(name = "so2Standard")
    private String so2Standard;

    /**
     * so2最近一次审核日期
     */
    @Column(name = "so2LastAuditDate")
    private String so2LastAuditDate;

    /**
     * so2合格情况
     */
    @Column(name = "so2LastAuditStatus")
    private String so2lastAuditStatus;

    /**
     * so2备注
     */
    @Column(name = "so2Mark")
    private String so2Mark;

    @Column(name = "x")
    private String x;

    @Column(name = "y")
    private String y;

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

    public String getDayCumulativeFlow48h() {
        return dayCumulativeFlow48h;
    }

    public void setDayCumulativeFlow48h(String dayCumulativeFlow48h) {
        this.dayCumulativeFlow48h = dayCumulativeFlow48h;
    }

    public String getDayCumulativeFlow30d() {
        return dayCumulativeFlow30d;
    }

    public void setDayCumulativeFlow30d(String dayCumulativeFlow30d) {
        this.dayCumulativeFlow30d = dayCumulativeFlow30d;
    }

    public String getGasFlow() {
        return gasFlow;
    }

    public void setGasFlow(String gasFlow) {
        this.gasFlow = gasFlow;
    }

    public String getGasFlow48h() {
        return gasFlow48h;
    }

    public void setGasFlow48h(String gasFlow48h) {
        this.gasFlow48h = gasFlow48h;
    }

    public String getGasFlow30d() {
        return gasFlow30d;
    }

    public void setGasFlow30d(String gasFlow30d) {
        this.gasFlow30d = gasFlow30d;
    }

    public String getNoDayMeanValue() {
        return noDayMeanValue;
    }

    public void setNoDayMeanValue(String noDayMeanValue) {
        this.noDayMeanValue = noDayMeanValue;
    }

    public String getNoDayMeanValue48h() {
        return noDayMeanValue48h;
    }

    public void setNoDayMeanValue48h(String noDayMeanValue48h) {
        this.noDayMeanValue48h = noDayMeanValue48h;
    }

    public String getNoDayMeanValue30d() {
        return noDayMeanValue30d;
    }

    public void setNoDayMeanValue30d(String noDayMeanValue30d) {
        this.noDayMeanValue30d = noDayMeanValue30d;
    }

    public String getNoStandard() {
        return noStandard;
    }

    public void setNoStandard(String noStandard) {
        this.noStandard = noStandard;
    }

    public String getNoLastAuditDate() {
        return noLastAuditDate;
    }

    public void setNoLastAuditDate(String noLastAuditDate) {
        this.noLastAuditDate = noLastAuditDate;
    }

    public String getNoLastAuditStatus() {
        return noLastAuditStatus;
    }

    public void setNoLastAuditStatus(String noLastAuditStatus) {
        this.noLastAuditStatus = noLastAuditStatus;
    }

    public String getNoMark() {
        return noMark;
    }

    public void setNoMark(String noMark) {
        this.noMark = noMark;
    }

    public String getSo2DayMeanValue() {
        return so2DayMeanValue;
    }

    public void setSo2DayMeanValue(String so2DayMeanValue) {
        this.so2DayMeanValue = so2DayMeanValue;
    }

    public String getSo2DayMeanValue48h() {
        return so2DayMeanValue48h;
    }

    public void setSo2DayMeanValue48h(String so2DayMeanValue48h) {
        this.so2DayMeanValue48h = so2DayMeanValue48h;
    }

    public String getSo2DayMeanValue30d() {
        return so2DayMeanValue30d;
    }

    public void setSo2DayMeanValue30d(String so2DayMeanValue30d) {
        this.so2DayMeanValue30d = so2DayMeanValue30d;
    }

    public String getSo2Standard() {
        return so2Standard;
    }

    public void setSo2Standard(String so2Standard) {
        this.so2Standard = so2Standard;
    }

    public String getSo2LastAuditDate() {
        return so2LastAuditDate;
    }

    public void setSo2LastAuditDate(String so2LastAuditDate) {
        this.so2LastAuditDate = so2LastAuditDate;
    }

    public String getSo2lastAuditStatus() {
        return so2lastAuditStatus;
    }

    public void setSo2lastAuditStatus(String so2lastAuditStatus) {
        this.so2lastAuditStatus = so2lastAuditStatus;
    }

    public String getSo2Mark() {
        return so2Mark;
    }

    public void setSo2Mark(String so2Mark) {
        this.so2Mark = so2Mark;
    }

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
