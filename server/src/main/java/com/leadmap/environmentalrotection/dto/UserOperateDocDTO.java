package com.leadmap.environmentalrotection.dto;

import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/11 17:26
 */
public class UserOperateDocDTO implements Serializable {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
     * 用户名
     */
    private String token;

    /**
     * 文档id
     */
    private String docId;

    /**
     * 是否点赞
     */
    private boolean isLike;

    /**
     * 是否已收藏
     */
    private boolean isCollect;
}
