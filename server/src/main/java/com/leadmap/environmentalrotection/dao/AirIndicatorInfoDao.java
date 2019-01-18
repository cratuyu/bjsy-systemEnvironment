package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.air.AirIndicatorInfo;
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
public interface AirIndicatorInfoDao extends PagingAndSortingRepository<AirIndicatorInfo, Long>, JpaSpecificationExecutor<AirIndicatorInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    AirIndicatorInfo findById(String id);

    /**
     * @param name
     * @return
     */
    List<AirIndicatorInfo> findByName(String name);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from AirIndicatorInfo t where id = :id")
    List<AirIndicatorInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

}
