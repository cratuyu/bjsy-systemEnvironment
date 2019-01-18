package com.leadmap.environmentalrotection.dto;

import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/11 14:58
 */
public class UserAuthInfo implements Serializable {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getIsCertification() {
        return isCertification;
    }

    public void setIsCertification(int isCertification) {
        this.isCertification = isCertification;
    }

    /**
     * 用户名
     */
    private String userName;

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * 头像
     */
    private String avatarUrl;


    /**
     * 是否已认证
     */
    private int isCertification;
}
