package com.leadmap.environmentalrotection.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/11/20 9:10
 */
public class GwtWaterInfoDTO implements Serializable {


    private Long stationId;

    private Date createTime;

    /**
     * 高锰酸盐指数
     */
    private String codmn;

    /**
     * 溶解氧
     */
    private String strDo;

    /**
     * 氨氮
     */
    private String nh4;


    /**
     * 测量时间
     */
    private String dateTime;

    /**
     * 水质类别
     */
    private String level;


    /**
     * ph
     */
    private String pH;


    /**
     * 断面名称
     */
    private String stationName;


    private String x;

    private String y;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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


    public String getpH() {
        return pH;
    }

    public void setpH(String pH) {
        this.pH = pH;
    }


    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }
}
