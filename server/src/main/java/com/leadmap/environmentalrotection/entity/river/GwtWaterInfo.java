package com.leadmap.environmentalrotection.entity.river;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leadmap.environmentalrotection.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 * 国家地表水水质信息类
 *
 * @author: ttq
 * @Date: 2018/7/9 13:31
 */
@Entity
@Table(name = "GwtWaterInfo")
public class GwtWaterInfo extends IdEntity implements Serializable {

    public String getCodmn() {
        return codmn;
    }

    public void setCodmn(String codmn) {
        this.codmn = codmn;
    }

    public String getStrDo() {
        return strDo;
    }

    public void setStrDo(String strDo) {
        this.strDo = strDo;
    }

    public String getNh4() {
        return nh4;
    }

    public void setNh4(String nh4) {
        this.nh4 = nh4;
    }

    public String getToc() {
        return toc;
    }

    public void setToc(String toc) {
        this.toc = toc;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelStatus() {
        return levelStatus;
    }

    public void setLevelStatus(String levelStatus) {
        this.levelStatus = levelStatus;
    }

    public String getpH() {
        return pH;
    }

    public void setpH(String pH) {
        this.pH = pH;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 高锰酸盐指数
     */
    @Column(name = "codmn")
    private String codmn;
//    @Transient
//    private String codmn48h;
//    @Transient
//    private String codmn30d;

    /**
     * 溶解氧
     */
    @Column(name = "strDo")
    private String strDo;
//    @Transient
//    private String strDo48h;
//    @Transient
//    private String strDo30d;

    /**
     * 氨氮
     */
    @Column(name = "nh4")
    private String nh4;
//    @Transient
//    private String nh448h;
//    @Transient
//    private String nh430d;

    /**
     * 总有机碳
     */
    @Column(name = "toc")
    private String toc;
//    @Transient
//    private String toc48h;
//    @Transient
//    private String toc30d;

    /**
     * 断面属性
     */
    @Column(name = "attribute")
    private String attribute;

    /**
     * 测量时间
     */
    @Column(name = "dateTime")
    private String dateTime;

    /**
     * 水质类别
     */
    @Column(name = "level")
    private String level;

    @Column(name = "levelStatus")
    private String levelStatus;

    /**
     * ph
     */
    @Column(name = "pH")
    private String pH;


//    @Transient
//    private String ph48h;
//    @Transient
//    private String ph30d;

    @Column(name = "siteCode")
    private String siteCode;

    /**
     * 断面名称
     */
    @Column(name = "siteName")
    private String siteName;

    /**
     * 站点情况
     */
    @Column(name = "status")
    private String status;

    @Transient
    @JsonIgnore
    private String DO;

    public String getDO() {
        return DO;
    }

    public void setDO(String DO) {
        this.DO = DO;
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

    @Column(name="x")
    private String x;
    @Column(name="y")
    private String y;

}
