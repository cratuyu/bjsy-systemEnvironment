package com.leadmap.management.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:版本信息
 *
 * @author: yxm
 * @Date: 2018/10/25 16:48
 */
@Entity
@Table(name = "version_info")
public class VersionInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 版本号
     */
    @Column(name="version_name")
    private String versionName;

    /**
     * 平台编号 IOS  Android
     */
    @Column(name="version_platform")
    private String versionPlatform;

    /**
     * 是否强制升级  1：升级 0：不升级
     */
    @Column(name="is_force_updata")
    private String isForceUpdata  ;

    /**
     * 版本描述
     */
    @Column(name="version_desc")
    private String versionDesc;

    /**
     * 版本下载地址
     */
    @Column(name="version_url")
    private String versionUrl;

    /**
     * 更新时间
     */
    @Column(name="update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionPlatform() {
        return versionPlatform;
    }

    public void setVersionPlatform(String versionPlatform) {
        this.versionPlatform = versionPlatform;
    }

    public String getIsForceUpdata() {
        return isForceUpdata;
    }

    public void setIsForceUpdata(String isForceUpdata) {
        this.isForceUpdata = isForceUpdata;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
