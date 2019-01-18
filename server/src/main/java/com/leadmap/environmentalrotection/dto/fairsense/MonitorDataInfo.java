package com.leadmap.environmentalrotection.dto.fairsense;

import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/17 17:48
 */
public class MonitorDataInfo implements Serializable {
    public MonitorValue getPM2_5() {
        return PM2_5;
    }

    public void setPM2_5(MonitorValue PM2_5) {
        this.PM2_5 = PM2_5;
    }

    public MonitorValue getSO2() {
        return SO2;
    }

    public void setSO2(MonitorValue SO2) {
        this.SO2 = SO2;
    }

    public MonitorValue getCO() {
        return CO;
    }

    public void setCO(MonitorValue CO) {
        this.CO = CO;
    }

    public MonitorValue getPM10() {
        return PM10;
    }

    public void setPM10(MonitorValue PM10) {
        this.PM10 = PM10;
    }

    public MonitorValue getO3() {
        return O3;
    }

    public void setO3(MonitorValue o3) {
        O3 = o3;
    }

    public MonitorValue getAQI() {
        return AQI;
    }

    public void setAQI(MonitorValue AQI) {
        this.AQI = AQI;
    }

    public MonitorValue getNO2() {
        return NO2;
    }

    public void setNO2(MonitorValue NO2) {
        this.NO2 = NO2;
    }

    public String getPrim() {
        return Prim;
    }

    public void setPrim(String prim) {
        Prim = prim;
    }

    private MonitorValue PM2_5;
    private MonitorValue SO2;
    private MonitorValue CO;
    private MonitorValue PM10;
    private MonitorValue O3;
    private MonitorValue AQI;
    private MonitorValue NO2;
    private String Prim;
}
