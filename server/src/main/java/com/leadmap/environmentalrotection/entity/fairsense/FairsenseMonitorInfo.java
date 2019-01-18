package com.leadmap.environmentalrotection.entity.fairsense;

import com.leadmap.environmentalrotection.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by John on 2018/8/27.
 */
@Entity
@Table(name = "FairsenseMonitorInfo")
public class FairsenseMonitorInfo extends IdEntity implements Serializable {
    @Column(name = "monitorId")
    private String monitorId;

    @Column(name = "monitorTime")
    private String monitorTime;

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        this.monitorTime = monitorTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column(name = "info")
    private String info;
}
