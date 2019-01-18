package com.leadmap.environmentalrotection.dto.air;

import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/12 20:17
 */
public class CityAirInfoDTO implements Serializable {
    public DataContainerDTO getAqi() {
        return aqi;
    }

    public void setAqi(DataContainerDTO aqi) {
        this.aqi = aqi;
    }

    public DataContainerDTO getPm25() {
        return pm25;
    }

    public void setPm25(DataContainerDTO pm25) {
        this.pm25 = pm25;
    }

    public DataContainerDTO getSo2() {
        return so2;
    }

    public void setSo2(DataContainerDTO so2) {
        this.so2 = so2;
    }

    public DataContainerDTO getNo2() {
        return no2;
    }

    public void setNo2(DataContainerDTO no2) {
        this.no2 = no2;
    }

    public DataContainerDTO getO3() {
        return o3;
    }

    public void setO3(DataContainerDTO o3) {
        this.o3 = o3;
    }

    public DataContainerDTO getCo() {
        return co;
    }

    public void setCo(DataContainerDTO co) {
        this.co = co;
    }

    public DataContainerDTO getPm10() {
        return pm10;
    }

    public void setPm10(DataContainerDTO pm10) {
        this.pm10 = pm10;
    }

    private DataContainerDTO aqi;
    private DataContainerDTO pm25;
    private DataContainerDTO so2;
    private DataContainerDTO no2;
    private DataContainerDTO o3;
    private DataContainerDTO co;
    private DataContainerDTO pm10;
}
