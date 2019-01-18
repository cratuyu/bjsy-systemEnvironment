package com.leadmap.mapservice.dao;


import com.leadmap.mapservice.entity.OpinionFeedback;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OpinionFeedbackDao extends PagingAndSortingRepository<OpinionFeedback, Long>, JpaSpecificationExecutor<OpinionFeedback> {

    @Query(value = "select count(id) from opinion_feedback", nativeQuery = true)
    Long getCount();

    void deleteAllById(String id);

    List<OpinionFeedback> findAllByOrderByIdDesc(Pageable page);

}
