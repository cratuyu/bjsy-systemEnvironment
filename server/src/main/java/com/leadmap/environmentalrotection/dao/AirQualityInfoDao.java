package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.weather.AirQualityInfo;
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
public interface AirQualityInfoDao extends PagingAndSortingRepository<AirQualityInfo, Long>, JpaSpecificationExecutor<AirQualityInfo> {
    /**
     * 根据id查找
     *
     * @param keyId
     * @param pageable
     * @return
     */
    @Query("from AirQualityInfo t where keyId = :keyId")
    List<AirQualityInfo> queryFamilyList(@Param("keyId") Long keyId, Pageable pageable);

    List<AirQualityInfo> findTop1ByOrderByCreateTimeDesc();

    @Query("from AirQualityInfo ")
    List<AirQualityInfo> getEducationAppInfoList();
}
