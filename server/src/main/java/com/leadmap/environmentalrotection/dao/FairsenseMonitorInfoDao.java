package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.fairsense.FairsenseMonitorInfo;
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
public interface FairsenseMonitorInfoDao extends PagingAndSortingRepository<FairsenseMonitorInfo, Long>, JpaSpecificationExecutor<FairsenseMonitorInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    FairsenseMonitorInfo findById(Long id);

    /**
     * @param monitorTime
     * @return
     */
    List<FairsenseMonitorInfo> findByMonitorTime(String monitorTime);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from FairsenseMonitorInfo t where id = :id")
    List<FairsenseMonitorInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

    List<FairsenseMonitorInfo> findTop1ByOrderByCreateTimeDesc();

}
