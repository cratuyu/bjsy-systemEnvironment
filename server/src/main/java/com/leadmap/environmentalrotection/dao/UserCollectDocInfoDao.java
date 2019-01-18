package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.user.UserCollectDocInfo;
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
public interface UserCollectDocInfoDao extends PagingAndSortingRepository<UserCollectDocInfo, Long>, JpaSpecificationExecutor<UserCollectDocInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    UserCollectDocInfo findById(Long id);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from UserCollectDocInfo t where id = :id")
    List<UserCollectDocInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

    /**
     * 根据docid和userId 查找
     * @param docId
     * @param userId
     * @return
     */
    UserCollectDocInfo findByDocIdAndUserId(String docId , String userId);
}
