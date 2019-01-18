package com.leadmap.mapservice.dao;

import com.leadmap.mapservice.entity.UserLikeDocInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/12/18 14:49
 */
public interface UserLikeDocDao extends PagingAndSortingRepository<UserLikeDocInfo, Long>, JpaSpecificationExecutor<UserLikeDocInfo> {

    /**
     *  删除文章已被删除的用户点赞信息
     * @param docId 文章id
     * @return
     */
    public void deleteByDocId(@Param("docId") String docId);

    public List<UserLikeDocInfo> getUserLikeDocInfoByDocId(@Param("docId") String docId);
}
