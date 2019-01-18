package com.leadmap.environmentalrotection.dto;

import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/11/20 13:18
 */
public class CountryMonitorWaterCompanyInfoDTO {

    private Long stationId;

    private Date createTime;

    /**
     * 企业名称
     */
    private String companyName;


    /**
     * 监控点名称
     */
    private String monitorName;

    /**
     * 监测日期
     */
    private String monitorDate;

    /**
     * cod排放标准
     */
    private String codStandard;

    /**
     * cod最近一次审核日期  nh4最近一次审核日期
     */
    private String dateTime;

    /**
     * cod合格情况
     */
    private String codLastAuditStatus;

    /**
     * nh4排放标准
     */
    private String nh4Standard;


    /**
     * nh4合格情况
     */
    private String nh4LastAuditStatus;

    /**
     * nh4浓度日均值
     */
    private String nh4DayMeanValue;

    /**
     * cod浓度日均值
     */
    private String codDayMeanValue;

    private String x;

    private String y;

    public String getNh4DayMeanValue() {
        return nh4DayMeanValue;
    }

    public void setNh4DayMeanValue(String nh4DayMeanValue) {
        this.nh4DayMeanValue = nh4DayMeanValue;
    }

    public String getCodDayMeanValue() {
        return codDayMeanValue;
    }

    public void setCodDayMeanValue(String codDayMeanValue) {
        this.codDayMeanValue = codDayMeanValue;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCodStandard() {
        return codStandard;
    }

    public void setCodStandard(String codStandard) {
        this.codStandard = codStandard;
    }


    public String getCodLastAuditStatus() {
        return codLastAuditStatus;
    }

    public void setCodLastAuditStatus(String codLastAuditStatus) {
        this.codLastAuditStatus = codLastAuditStatus;
    }

    public String getNh4Standard() {
        return nh4Standard;
    }

    public void setNh4Standard(String nh4Standard) {
        this.nh4Standard = nh4Standard;
    }


    public String getNh4LastAuditStatus() {
        return nh4LastAuditStatus;
    }

    public void setNh4LastAuditStatus(String nh4LastAuditStatus) {
        this.nh4LastAuditStatus = nh4LastAuditStatus;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
