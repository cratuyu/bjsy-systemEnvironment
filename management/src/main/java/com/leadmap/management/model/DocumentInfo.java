package com.leadmap.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leadmap.management.dto.UserCollectInfoDTO;
import com.leadmap.management.dto.UserCommentInfoDTO;
import com.leadmap.management.dto.UserLikeInfoDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/9 16:53
 */
@Entity
@Table(name = "document_info")
public class DocumentInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 内容
     */
    @Column(name = "content")
    @JsonIgnore
    private String content;

    /**
     * 类别
     */
    @Column(name = "type")
    private String type;

    /**
     * 转载时间
     */
    @Column(name = "publish_date")
    private String publishDate;

    /**
     * 创建时间
     */
    @Column(name = "create_Time")
    private Date createTime;

    /**
     * url
     */
    @Column(name = "document_url")
    private String documentUrl;

    /**
     * 发布人
     */
    @Column(name = "publisher")
    private String publisher;

    @Column(name = "image_url")
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

//    public UserOperateDocInfo getUserOperateDocInfo() {
//        return userOperateDocInfo;
//    }
//
//    public void setUserOperateDocInfo(UserOperateDocInfo userOperateDocInfo) {
//        this.userOperateDocInfo = userOperateDocInfo;
//    }

    public List<UserCommentInfoDTO> getUserCommentInfoList() {
        return userCommentInfoList;
    }

    public void setUserCommentInfoList(List<UserCommentInfoDTO> userCommentInfoList) {
        this.userCommentInfoList = userCommentInfoList;
    }

//    @Transient
//    private UserOperateDocInfo userOperateDocInfo;

    @Transient
    @JsonIgnore
    private List<UserCommentInfoDTO> userCommentInfoList;

    @Transient
    @JsonIgnore
    private long userCommentCount;

    public long getUserCommentCount() {
        return userCommentCount;
    }

    public void setUserCommentCount(long userCommentCount) {
        this.userCommentCount = userCommentCount;
    }

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

    @Transient
    private long userLikeCount;

    @Transient
    private long userCollectCount;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<UserLikeInfoDTO> getUserLikeInfoDTOList() {
        return userLikeInfoDTOList;
    }

    public void setUserLikeInfoDTOList(List<UserLikeInfoDTO> userLikeInfoDTOList) {
        this.userLikeInfoDTOList = userLikeInfoDTOList;
    }

    public List<UserCollectInfoDTO> getUserCollectInfoDTOList() {
        return userCollectInfoDTOList;
    }

    public void setUserCollectInfoDTOList(List<UserCollectInfoDTO> userCollectInfoDTOList) {
        this.userCollectInfoDTOList = userCollectInfoDTOList;
    }

    @Transient
    private List<UserLikeInfoDTO> userLikeInfoDTOList;

    @Transient
    private List<UserCollectInfoDTO> userCollectInfoDTOList;

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

    @Transient
    private boolean isLike;

    @Transient
    private boolean isCollect;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
