package com.leadmap.mapservice.dao;

import com.leadmap.mapservice.entity.UserCollectDocInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/12/18 14:49
 */
public interface UserCollectDocDao extends PagingAndSortingRepository<UserCollectDocInfo, Long>, JpaSpecificationExecutor<UserCollectDocInfo> {


    /**
     * 删除文章被删除的 用户收藏记录
     * @param docId 文章id
     * @return
     */
    public void deleteByDocId(@Param("docId") String docId);

    public List<UserCollectDocInfo> getUserCollectDocInfoByDocId(@Param("docId") String docId);
}
