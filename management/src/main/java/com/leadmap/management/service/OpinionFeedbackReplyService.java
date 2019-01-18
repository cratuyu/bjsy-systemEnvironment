package com.leadmap.management.service;

import com.leadmap.management.model.OpinionFeedbackInfoReply;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/12/26 15:33
 */
public interface OpinionFeedbackReplyService extends IService<OpinionFeedbackInfoReply> {

    void addOpinionFeedbackReply(OpinionFeedbackInfoReply opinionFeedbackInfoReply);
}
