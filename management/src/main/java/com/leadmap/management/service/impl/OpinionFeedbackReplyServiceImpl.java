package com.leadmap.management.service.impl;

import com.leadmap.management.mapper.OpinionFeedbackReplyMapper;
import com.leadmap.management.model.OpinionFeedbackInfoReply;
import com.leadmap.management.service.OpinionFeedbackReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/12/26 15:31
 */
@Service("opinionFeedbackReplyService")
public class OpinionFeedbackReplyServiceImpl extends BaseService<OpinionFeedbackInfoReply> implements OpinionFeedbackReplyService {

    @Resource
    private OpinionFeedbackReplyMapper opinionFeedbackReplyMapper;

    @Override
    public void addOpinionFeedbackReply(OpinionFeedbackInfoReply opinionFeedbackInfoReply) {
        opinionFeedbackReplyMapper.addOpinionFeedbackReply(opinionFeedbackInfoReply);
    }

}
