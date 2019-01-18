package com.leadmap.environmentalrotection.entity.weather;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:街道信息
 *
 * @author: yxm
 * @Date: 2018/10/17 18:29
 */
@Entity
@Table(name = "AvenueInfo")
public class AvenueInfo implements Serializable {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 街道id
     */
    @NotBlank
    @javax.persistence.Column(name = "avenueId")
    private String avenueId;

    /**
     * 街道名称名称
     */
    @NotBlank
    @javax.persistence.Column(name = "avenueName")
    private String avenueName;

    /**
     * x坐标
     */
    @Column(name = "x")
    private String x;

    /**
     * y坐标
     */
    @Column(name = "y")
    private String y;

    /**
     * 监测点
     */
    @Column(name = "monitoringSite")
    private String monitoringSite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvenueId() {
        return avenueId;
    }

    public void setAvenueId(String avenueId) {
        this.avenueId = avenueId;
    }

    public String getAvenueName() {
        return avenueName;
    }

    public void setAvenueName(String avenueName) {
        this.avenueName = avenueName;
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

    public String getMonitoringSite() {
        return monitoringSite;
    }

    public void setMonitoringSite(String monitoringSite) {
        this.monitoringSite = monitoringSite;
    }
}
