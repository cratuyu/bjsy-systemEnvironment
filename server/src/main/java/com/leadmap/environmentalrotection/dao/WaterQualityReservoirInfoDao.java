package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.river.WaterQualityReservoirInfo;
import com.leadmap.environmentalrotection.entity.river.RiverWaterQualityInfo;
import com.leadmap.environmentalrotection.entity.river.WaterQualityReservoirInfo;
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
public interface WaterQualityReservoirInfoDao extends PagingAndSortingRepository<WaterQualityReservoirInfo, Long>, JpaSpecificationExecutor<WaterQualityReservoirInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    WaterQualityReservoirInfo findById(String id);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from WaterQualityReservoirInfo t where id = :id")
    List<WaterQualityReservoirInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

}
