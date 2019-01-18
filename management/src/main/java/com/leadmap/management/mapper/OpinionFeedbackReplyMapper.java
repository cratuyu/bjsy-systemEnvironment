package com.leadmap.management.mapper;

import com.leadmap.management.model.OpinionFeedbackInfoReply;
import com.leadmap.management.model.VersionInfo;
import com.leadmap.management.util.MyMapper;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface OpinionFeedbackReplyMapper extends MyMapper<OpinionFeedbackInfoReply> {

    VersionInfo addOpinionFeedbackReply(OpinionFeedbackInfoReply opinionFeedbackInfoReply);

}