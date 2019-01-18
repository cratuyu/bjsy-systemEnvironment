package com.leadmap.mapservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/20 10:34
 */
@Entity
@Table(name="documentInfo")
public class DocumentInfo extends IdEntity implements Serializable {

    @Column(name = "content")
    private String content;

    /**
     * 文章url

     */
    @Column(name = "documentUrl")
    private String documentUrl;

    /**
     * 图片url
     */
    @Column(name = "imageUrl")
    private String imageUrl;

    /**
     * 发表日期
     */
    @Column(name="publishDate")
    private String publishDate;

    /**
     * 出版人
     */
    @Column(name = "publisher")
    private String publisher;

    /**
     * 文章标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 类型
     */
    @Column(name = "type")
    private String type;

    public void setContent(String content) {
        this.content = content;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {

        return content;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
}
