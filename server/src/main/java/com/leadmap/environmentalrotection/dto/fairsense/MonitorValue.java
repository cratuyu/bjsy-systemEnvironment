package com.leadmap.environmentalrotection.dto.fairsense;

import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/17 17:42
 */
public class MonitorValue implements Serializable {
    public String getIAQI() {
        return IAQI;
    }

    public void setIAQI(String IAQI) {
        this.IAQI = IAQI;
    }

    public boolean getIsPri() {
        return isPri;
    }

    public void setIsPri(boolean pri) {
        isPri = pri;
    }

    public float getValue() {
        return Value;
    }

    public void setValue(float value) {
        Value = value;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    private String IAQI;
    private boolean isPri;
    private float Value;
    private int Level;
}
