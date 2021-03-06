package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.document.DocumentInfo;
import com.leadmap.environmentalrotection.entity.user.UserCommentInfo;
import com.leadmap.environmentalrotection.entity.user.UserOperateDocInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/5/17 10:12
 */

@Repository
public interface UserCommentInfoDao extends PagingAndSortingRepository<UserCommentInfo, Long>, JpaSpecificationExecutor<UserCommentInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    UserCommentInfo findById(String id);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from UserCommentInfo t where id = :id")
    List<UserCommentInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

}
