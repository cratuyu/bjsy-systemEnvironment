package com.leadmap.environmentalrotection.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:意见反馈
 *
 * @author: yxm
 * @Date: 2018/10/24 12:14
 */
@Entity
@Table(name = "opinion_feedback_info_reply")
public class OpinionFeedbackInfoReply implements Serializable {

    @Id
    private String id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;


    /**
     * 回复内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 意见表id
     */
    @Column(name = "op_feed_id")
    private String opFeedId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOpFeedId() {
        return opFeedId;
    }

    public void setOpFeedId(String opFeedId) {
        this.opFeedId = opFeedId;
    }
}
