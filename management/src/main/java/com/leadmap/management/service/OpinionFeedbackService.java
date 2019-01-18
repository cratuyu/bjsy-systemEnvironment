package com.leadmap.management.service;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.OpinionFeedback;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface OpinionFeedbackService extends IService<OpinionFeedback> {

    PageInfo<OpinionFeedback> selectByPage(OpinionFeedback opinionFeedback, int start, int length);

}
