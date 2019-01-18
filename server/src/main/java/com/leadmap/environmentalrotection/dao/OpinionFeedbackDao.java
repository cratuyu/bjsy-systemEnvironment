package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.user.OpinionFeedback;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/24 13:01
 */
@Repository
public interface OpinionFeedbackDao extends PagingAndSortingRepository<OpinionFeedback, String>, JpaSpecificationExecutor<OpinionFeedback> {

    List<OpinionFeedback> queryOpinionFeedbackByContent(String content);

    @Modifying
    @Transactional
    @Query(value = "select * from opinion_feedback ", nativeQuery = true)
    List<OpinionFeedback> queryOpinionFeedback();

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    OpinionFeedback findById(String id);


}
