package com.leadmap.management.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/11/5 19:04
 */
@Entity
@Table(name = "operation_log")
public class OperationLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @Column(name ="user_name")
    private String userName;

    /**
     * 创建时间
     */
    @Column(name ="create_time")
    private Date createTime;

    /**
     * 用户IP
     */
    @Column(name ="ip")
    private String ip;

    /**
     * url
     */
    @Column(name ="url")
    private String url;

    /**
     * Get 还是 pos请求
     */
    @Column(name ="method")
    private String method;

    /**
     * 方法名
     */
    @Column(name ="method_name")
    private String methodName;

    /**
     * 日志名称
     */
    @Column(name ="log_name")
    private String logName;

    /**
     * 日志类型  操作日志，异常日志
     */
    @Column(name ="log_sign")
    private String logSign;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getLogSign() {
        return logSign;
    }

    public void setLogSign(String logSign) {
        this.logSign = logSign;
    }
}
