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
 * @Date: 2018/12/21 9:37
 */
@Entity
@Table(name = "OperationLog")
public class UserOperation extends IdEntity implements Serializable {

    @Column(name = "ip")
    private String ip;

    @Column(name = "url")
    private String url;

    @Column(name = "userName")
    private String userName;

    @Column(name = "logName")
    private String logName;

    @Column(name = "logSign")
    private String logSign;

    @Column(name = "method")
    private String method;

    @Column(name = "methodName")
    private String methodName;

    public void setMethod(String method) {
        this.method = method;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethod() {

        return method;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public void setLogSign(String logSign) {
        this.logSign = logSign;
    }


    public String getIp() {
        return ip;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getLogName() {
        return logName;
    }

    public String getLogSign() {
        return logSign;
    }
}
