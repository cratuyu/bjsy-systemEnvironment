package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.user.OpinionFeedbackInfoReply;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/24 13:01
 */
@Repository
public interface OpinionFeedbackReplyDao extends PagingAndSortingRepository<OpinionFeedbackInfoReply, String>, JpaSpecificationExecutor<OpinionFeedbackInfoReply> {



}
