package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.user.UserInfo;
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
public interface UserOperateDocInfoDao extends PagingAndSortingRepository<UserOperateDocInfo, Long>, JpaSpecificationExecutor<UserOperateDocInfo> {

    /**
     * 根据id查找
     * @param id
     * @return
     */
    UserOperateDocInfo findById(String id);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from UserOperateDocInfo t where id = :id")
    List<UserOperateDocInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

}
