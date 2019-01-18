package com.leadmap.erawl.entity.river;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @Date: 2018/9/10 13:47
 */
@Entity
@Table(name = "GwtWaterInfo")
public class GwtWaterInfo extends IdEntity {

    /**
     * 高锰酸盐指数
     */
    @Column(name = "codmn")
    private String codmn;

    @Transient
    private String codmn48h;

    @Transient
    private String codmn30d;

    /**
     * 溶解氧
     */
    @Column(name = "strDo")
    private String strDo;

    @Transient
    private String strDo48h;

    @Transient
    private String strDo30d;

    /**
     * 氨氮
     */
    @Column(name = "nh4")
    private String nh4;

    @Transient
    private String nh448h;

    @Transient
    private String nh430d;

    /**
     * 总有机碳
     */
    @Column(name = "toc")
    private String toc;

    @Transient
    private String toc48h;

    @Transient
    private String toc30d;

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

    @Transient
    private String ph48h;

    @Transient
    private String ph30d;

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

    @Column(name="x")
    private String x;

    @Column(name="y")
    private String y;

    public String getCodmn() {
        return codmn;
    }

    public void setCodmn(String codmn) {
        this.codmn = codmn;
    }

    public String getCodmn48h() {
        return codmn48h;
    }

    public void setCodmn48h(String codmn48h) {
        this.codmn48h = codmn48h;
    }

    public String getCodmn30d() {
        return codmn30d;
    }

    public void setCodmn30d(String codmn30d) {
        this.codmn30d = codmn30d;
    }

    public String getStrDo() {
        return strDo;
    }

    public void setStrDo(String strDo) {
        this.strDo = strDo;
    }

    public String getStrDo48h() {
        return strDo48h;
    }

    public void setStrDo48h(String strDo48h) {
        this.strDo48h = strDo48h;
    }

    public String getStrDo30d() {
        return strDo30d;
    }

    public void setStrDo30d(String strDo30d) {
        this.strDo30d = strDo30d;
    }

    public String getNh4() {
        return nh4;
    }

    public void setNh4(String nh4) {
        this.nh4 = nh4;
    }

    public String getNh448h() {
        return nh448h;
    }

    public void setNh448h(String nh448h) {
        this.nh448h = nh448h;
    }

    public String getNh430d() {
        return nh430d;
    }

    public void setNh430d(String nh430d) {
        this.nh430d = nh430d;
    }

    public String getToc() {
        return toc;
    }

    public void setToc(String toc) {
        this.toc = toc;
    }

    public String getToc48h() {
        return toc48h;
    }

    public void setToc48h(String toc48h) {
        this.toc48h = toc48h;
    }

    public String getToc30d() {
        return toc30d;
    }

    public void setToc30d(String toc30d) {
        this.toc30d = toc30d;
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

    public String getPh48h() {
        return ph48h;
    }

    public void setPh48h(String ph48h) {
        this.ph48h = ph48h;
    }

    public String getPh30d() {
        return ph30d;
    }

    public void setPh30d(String ph30d) {
        this.ph30d = ph30d;
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
}
