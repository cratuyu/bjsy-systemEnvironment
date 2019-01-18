package com.leadmap.environmentalrotection.dto;

import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/16 18:10
 */
public class DocumentSimpleInfoDTO implements Serializable {
    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    private Long docId;

    private String title;

    private String imageUrl;

    private boolean isLike;

    private boolean isCollect;

    private String publishDate;

    public long getUserLikeCount() {
        return userLikeCount;
    }

    public void setUserLikeCount(long userLikeCount) {
        this.userLikeCount = userLikeCount;
    }

    public long getUserCollectCount() {
        return userCollectCount;
    }

    public void setUserCollectCount(long userCollectCount) {
        this.userCollectCount = userCollectCount;
    }

    private long userLikeCount;

    private long userCollectCount;

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    private String docUrl;

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }
}
