package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.user.UserLikeDocInfo;
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
public interface UserLikeDocInfoDao extends PagingAndSortingRepository<UserLikeDocInfo, Long>, JpaSpecificationExecutor<UserLikeDocInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    UserLikeDocInfo findById(Long id);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from UserLikeDocInfo t where id = :id")
    List<UserLikeDocInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

    /**
     * 根据docid和userId 查找
     * @param docId
     * @param userId
     * @return
     */
    UserLikeDocInfo findByDocIdAndUserId(String docId,String userId);


}
