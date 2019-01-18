package com.leadmap.environmentalrotection.entity.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/9 14:20
 */
@Entity
@Table(name = "ip_info")
public class IpInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 登录次数
     */
    @Column(name = "count")
    private int count;

    /**
     * 1:表示游客
     * 2:表示注册用户
     */
    @Column(name = "flag")
    private int flag;

    /**
     * IOS终端还是Android终端访问
     */
    @Column(name = "user_agent")
    private String userAgent;

    /**
     * 用户名
     */
    @Column(name = "userName")
    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
