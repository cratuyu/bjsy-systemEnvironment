package com.leadmap.environmentalrotection.entity.user;

import com.leadmap.environmentalrotection.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/11 14:48
 */
@Entity
@Table(name = "UserInfo")
public class UserInfo extends IdEntity implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    /**
     * 用户名
     */
    @Column(name = "userName")
    private String userName;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * 头像
     */
    @Column(name = "avatarUrl")
    private String avatarUrl;

    public String getSMSVerificationCode() {
        return SMSVerificationCode;
    }

    public void setSMSVerificationCode(String SMSVerificationCode) {
        this.SMSVerificationCode = SMSVerificationCode;
    }

    /**
     * 短信验证码
     */
    @Column(name = "SMSVerificationCode")
    private String SMSVerificationCode;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 用户类型：password,sms,qq,微信
     */
    private String type;

    /**
     * 表用户唯一身份的ID
     */
    private String openId;

    /**
     * 调用接口需要用到的token，比如利用accessToken发表微博等，如果只是对接登录的话，这个其实没啥用
     */
    private String accessToken;

    /**
     * 授权过期时间，第三方登录授权都是有过期时间的，比如3个月
     */
    private Date expiredTime;

    /**
     * 昵称
     */
    private String nickName;
}
