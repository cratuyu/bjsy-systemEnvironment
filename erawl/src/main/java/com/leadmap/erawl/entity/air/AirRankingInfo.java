package com.leadmap.erawl.entity.air;


import com.leadmap.erawl.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/11 10:41
 */
@Entity
@Table(name = "AirRankingInfo")
public class AirRankingInfo extends IdEntity {

    @Column(name = "indexValue")
    private String indexValue;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "aqi")
    private String aqi;

    @Column(name = "level")
    private String level;

    @Column(name = "pm25")
    private String pm25;

    @Column(name = "updateTime")
    private String updateTime;

    public String getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
