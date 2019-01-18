package com.leadmap.erawl.entity.weather;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/9 11:02
 */
@Entity
@Table(name = "AirQualityInfo")
public class AirQualityInfo implements Serializable {
    public AirQualityInfo() {
        this.createTime = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyId;

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;

    @Column(name = "pm1024h")
    private String pm1024h;

    @Column(name = "pm10avg")
    private String pm10avg;

    @Column(name = "pm10level")
    private String pm10level;

    @Column(name = "pm10quality")
    private String pm10quality;

    @Column(name = "pm10facesign")
    private String pm10facesign;

    @Column(name = "so224h")
    private String so224h;

    @Column(name = "so2avg")
    private String so2avg;

    @Column(name = "so2level")
    private String so2level;

    @Column(name = "so2quality")
    private String so2quality;

    @Column(name = "so2facesign")
    private String so2facesign;

    @Column(name = "pm2524h")
    private String pm2524h;

    @Column(name = "pm25avg")
    private String pm25avg;

    @Column(name = "o324h")
    private String o324h;

    @Column(name = "o3avg")
    private String o3avg;

    @Column(name = "o3level")
    private String o3level;

    @Column(name = "o3quality")
    private String o3quality;

    @Column(name = "o3facesign")
    private String o3facesign;

    @Column(name = "no224h")
    private String no224h;

    @Column(name = "no2avg")
    private String no2avg;

    @Column(name = "no2level")
    private String no2level;

    @Column(name = "no2quality")
    private String no2quality;

    @Column(name = "no2facesign")
    private String no2facesign;

    @Column(name = "co24h")
    private String co24h;

    @Column(name = "coavg")
    private String coavg;

    @Column(name = "colevel")
    private String colevel;

    @Column(name = "coquality")
    private String coquality;

    @Column(name = "cofacesign")
    private String cofacesign;

    @Column(name = "date_f")
    private String date_f;

    @Column(name = "id")
    private String id;

    @Column(name = "aqi")
    private String aqi;

    @Column(name = "so2")
    private String so2;

    @Column(name = "first")
    private String first;

    @Column(name = "grade")
    private String grade;

    @Column(name = "pm10")
    private String pm10;

    @Column(name = "pm2")
    private String pm2;

    @Column(name = "no2")
    private String no2;

    @Column(name = "o3")
    private String o3;

    @Column(name = "co")
    private String co;

    @Column(name = "pm10_01")
    private String pm10_01;

    @Column(name = "pm2_01")
    private String pm2_01;

    @Column(name = "no2_01")
    private String no2_01;

    public String getPm1024h() {
        return pm1024h;
    }

    public void setPm1024h(String pm1024h) {
        this.pm1024h = pm1024h;
    }

    public String getPm10avg() {
        return pm10avg;
    }

    public void setPm10avg(String pm10avg) {
        this.pm10avg = pm10avg;
    }

    public String getPm10level() {
        return pm10level;
    }

    public void setPm10level(String pm10level) {
        this.pm10level = pm10level;
    }

    public String getPm10quality() {
        return pm10quality;
    }

    public void setPm10quality(String pm10quality) {
        this.pm10quality = pm10quality;
    }

    public String getPm10facesign() {
        return pm10facesign;
    }

    public void setPm10facesign(String pm10facesign) {
        this.pm10facesign = pm10facesign;
    }

    public String getSo224h() {
        return so224h;
    }

    public void setSo224h(String so224h) {
        this.so224h = so224h;
    }

    public String getSo2avg() {
        return so2avg;
    }

    public void setSo2avg(String so2avg) {
        this.so2avg = so2avg;
    }

    public String getSo2level() {
        return so2level;
    }

    public void setSo2level(String so2level) {
        this.so2level = so2level;
    }

    public String getSo2quality() {
        return so2quality;
    }

    public void setSo2quality(String so2quality) {
        this.so2quality = so2quality;
    }

    public String getSo2facesign() {
        return so2facesign;
    }

    public void setSo2facesign(String so2facesign) {
        this.so2facesign = so2facesign;
    }

    public String getPm2524h() {
        return pm2524h;
    }

    public void setPm2524h(String pm2524h) {
        this.pm2524h = pm2524h;
    }

    public String getPm25avg() {
        return pm25avg;
    }

    public void setPm25avg(String pm25avg) {
        this.pm25avg = pm25avg;
    }

    public String getO324h() {
        return o324h;
    }

    public void setO324h(String o324h) {
        this.o324h = o324h;
    }

    public String getO3avg() {
        return o3avg;
    }

    public void setO3avg(String o3avg) {
        this.o3avg = o3avg;
    }

    public String getO3level() {
        return o3level;
    }

    public void setO3level(String o3level) {
        this.o3level = o3level;
    }

    public String getO3quality() {
        return o3quality;
    }

    public void setO3quality(String o3quality) {
        this.o3quality = o3quality;
    }

    public String getO3facesign() {
        return o3facesign;
    }

    public void setO3facesign(String o3facesign) {
        this.o3facesign = o3facesign;
    }

    public String getNo224h() {
        return no224h;
    }

    public void setNo224h(String no224h) {
        this.no224h = no224h;
    }

    public String getNo2avg() {
        return no2avg;
    }

    public void setNo2avg(String no2avg) {
        this.no2avg = no2avg;
    }

    public String getNo2level() {
        return no2level;
    }

    public void setNo2level(String no2level) {
        this.no2level = no2level;
    }

    public String getNo2quality() {
        return no2quality;
    }

    public void setNo2quality(String no2quality) {
        this.no2quality = no2quality;
    }

    public String getNo2facesign() {
        return no2facesign;
    }

    public void setNo2facesign(String no2facesign) {
        this.no2facesign = no2facesign;
    }

    public String getCo24h() {
        return co24h;
    }

    public void setCo24h(String co24h) {
        this.co24h = co24h;
    }

    public String getCoavg() {
        return coavg;
    }

    public void setCoavg(String coavg) {
        this.coavg = coavg;
    }

    public String getColevel() {
        return colevel;
    }

    public void setColevel(String colevel) {
        this.colevel = colevel;
    }

    public String getCoquality() {
        return coquality;
    }

    public void setCoquality(String coquality) {
        this.coquality = coquality;
    }

    public String getCofacesign() {
        return cofacesign;
    }

    public void setCofacesign(String cofacesign) {
        this.cofacesign = cofacesign;
    }

    public String getDate_f() {
        return date_f;
    }

    public void setDate_f(String date_f) {
        this.date_f = date_f;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm2() {
        return pm2;
    }

    public void setPm2(String pm2) {
        this.pm2 = pm2;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getPm10_01() {
        return pm10_01;
    }

    public void setPm10_01(String pm10_01) {
        this.pm10_01 = pm10_01;
    }

    public String getPm2_01() {
        return pm2_01;
    }

    public void setPm2_01(String pm2_01) {
        this.pm2_01 = pm2_01;
    }

    public String getNo2_01() {
        return no2_01;
    }

    public void setNo2_01(String no2_01) {
        this.no2_01 = no2_01;
    }

    public String getO3_01() {
        return o3_01;
    }

    public void setO3_01(String o3_01) {
        this.o3_01 = o3_01;
    }

    public String getCo_01() {
        return co_01;
    }

    public void setCo_01(String co_01) {
        this.co_01 = co_01;
    }

    public String getSo2_01() {
        return so2_01;
    }

    public void setSo2_01(String so2_01) {
        this.so2_01 = so2_01;
    }

    public String getPm10_02() {
        return pm10_02;
    }

    public void setPm10_02(String pm10_02) {
        this.pm10_02 = pm10_02;
    }

    public String getPm2_02() {
        return pm2_02;
    }

    public void setPm2_02(String pm2_02) {
        this.pm2_02 = pm2_02;
    }

    public String getNo2_02() {
        return no2_02;
    }

    public void setNo2_02(String no2_02) {
        this.no2_02 = no2_02;
    }

    public String getO3_02() {
        return o3_02;
    }

    public void setO3_02(String o3_02) {
        this.o3_02 = o3_02;
    }

    public String getCo_02() {
        return co_02;
    }

    public void setCo_02(String co_02) {
        this.co_02 = co_02;
    }

    public String getSo2_02() {
        return so2_02;
    }

    public void setSo2_02(String so2_02) {
        this.so2_02 = so2_02;
    }

    private String o3_01;

    private String co_01;

    private String so2_01;

    private String pm10_02;

    private String pm2_02;

    private String no2_02;

    private String o3_02;

    private String co_02;

    private String so2_02;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public boolean isCountryStation() {
        return isCountryStation;
    }

    public void setCountryStation(boolean countryStation) {
        isCountryStation = countryStation;
    }

    private String stationName;
    private boolean isCountryStation;

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

    @javax.persistence.Column(name = "x")
    private String x;

    @javax.persistence.Column(name = "y")
    private String y;

    @javax.persistence.Column(name = "createTime")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @javax.persistence.Column(name = "aqi24h")
    private String aqi24h;

    public String getAqi24h() {
        return aqi24h;
    }

    public void setAqi24h(String aqi24h) {
        this.aqi24h = aqi24h;
    }
}
