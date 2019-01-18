package com.leadmap.environmentalrotection.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/11 16:36
 */
@Entity
@Table(name = "UserOperateDocInfo")
public class UserOperateDocInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(boolean like) {
        isLike = like;
    }

    public boolean getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean collect) {
        isCollect = collect;
    }

    /**
     * 用户id
     */
    @Column(name = "userId")
    private String userId;

    /**
     * 文档id
     */
    @Column(name = "docId")
    private String docId;

    /**
     * 是否点赞
     */
    @Column(name = "getIsLike")
    private boolean isLike;

    /**
     * 是否已收藏
     */
    @Column(name = "getIsCollect")
    private boolean isCollect;
}
