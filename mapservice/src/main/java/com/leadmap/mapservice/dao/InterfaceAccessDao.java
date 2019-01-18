package com.leadmap.mapservice.dao;


import com.leadmap.mapservice.entity.InterfaceAccess;
import org.springframework.data.domain.Pageable;
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
 * @Date: 2018/12/28 13:49
 */

@Repository
public interface InterfaceAccessDao extends PagingAndSortingRepository<InterfaceAccess, Long>, JpaSpecificationExecutor<InterfaceAccess> {

    @Query("select count(id) from InterfaceAccess")
    Long getCount();

    List<InterfaceAccess> findAllByOrderByIdDesc(Pageable var2);
}
