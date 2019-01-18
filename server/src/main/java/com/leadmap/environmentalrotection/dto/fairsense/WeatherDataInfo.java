package com.leadmap.environmentalrotection.dto.fairsense;

import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/17 17:46
 */
public class WeatherDataInfo implements Serializable {
    public String getTEMP() {
        return TEMP;
    }

    public void setTEMP(String TEMP) {
        this.TEMP = TEMP;
    }

    public String getRH() {
        return RH;
    }

    public void setRH(String RH) {
        this.RH = RH;
    }

    private String TEMP;
    private String RH;
}
