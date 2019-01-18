package com.leadmap.environmentalrotection.dto;

import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/25 19:38
 */
public class VersionInfoDTO implements Serializable {

    private String versionName;

    private String versionPlatform;

    private boolean isForceUpdata  ;

    private String versionUrl;

    private String versionDesc;

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

    public boolean isForceUpdata() {
        return isForceUpdata;
    }

    public void setForceUpdata(boolean forceUpdata) {
        isForceUpdata = forceUpdata;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }
}
