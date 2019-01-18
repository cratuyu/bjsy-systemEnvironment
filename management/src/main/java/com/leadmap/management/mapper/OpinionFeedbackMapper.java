package com.leadmap.management.mapper;

import com.leadmap.management.model.OpinionFeedback;
import com.leadmap.management.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface OpinionFeedbackMapper extends MyMapper<OpinionFeedback> {

    List<OpinionFeedback> selectByOpinionFeedback(@Param("opinionFeedback")OpinionFeedback opinionFeedback);

}