package com.leadmap.mapservice.dao;

import com.leadmap.mapservice.entity.UserOperation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/21 9:40
 */
@Repository
public interface UserOperationDao extends PagingAndSortingRepository<UserOperation, Long>, JpaSpecificationExecutor<UserOperation> {

    /**
     * 返回全部用户操作信息
     * @return
     */
    @Query("from UserOperation")
    List<UserOperation> getUserOperationList();

    @Query("select count(id) from UserOperation ")
    Long getCount();
}
