package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.fairsense.FairsenseStationInfo;
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
public interface FairsenseStationInfoDao extends PagingAndSortingRepository<FairsenseStationInfo, Long>, JpaSpecificationExecutor<FairsenseStationInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    FairsenseStationInfo findById(Long id);


    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from FairsenseStationInfo t where id = :id")
    List<FairsenseStationInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);
}
