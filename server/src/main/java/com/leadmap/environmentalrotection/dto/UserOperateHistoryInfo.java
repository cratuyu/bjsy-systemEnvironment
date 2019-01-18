package com.leadmap.environmentalrotection.dto;

import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 * 用户操作历史记录
 *
 * @author: ttq
 * @Date: 2018/7/13 8:49
 */
public class UserOperateHistoryInfo implements Serializable {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    private String userName;
    private String docId;
    private String docTitle;
    private String publisher;
    private String documentUrl;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    private String publishDate;

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
}
