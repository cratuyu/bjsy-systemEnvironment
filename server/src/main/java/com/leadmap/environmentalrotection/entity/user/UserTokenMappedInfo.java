package com.leadmap.environmentalrotection.entity.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/13 14:44
 */
@Entity
@Table(name = "UserTokenMappedInfo")
public class UserTokenMappedInfo implements Serializable {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "token")
    private String token;
    @Column(name = "userId")
    private Long userId;
}
