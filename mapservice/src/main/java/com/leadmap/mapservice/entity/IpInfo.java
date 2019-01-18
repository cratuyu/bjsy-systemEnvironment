package com.leadmap.mapservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/20 16:56
 */
@Entity
@Table(name = "IpInfo")
public class IpInfo extends IdEntity implements Serializable {

    @Column(name = "userName")
    private String userName;

    @Column(name = "userAgent")
    private String userAgent;

    @Column(name = "updateTime")
    private String updateTime;

    @Column(name = "ip")
    private String ip;

    @Column(name = "flag")
    private int flag;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {

        return flag;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {

        return userName;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getIp() {
        return ip;
    }
}
