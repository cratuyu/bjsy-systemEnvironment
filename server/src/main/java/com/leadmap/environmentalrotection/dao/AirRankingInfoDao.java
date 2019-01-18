package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.air.AirRankingInfo;
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
public interface AirRankingInfoDao extends PagingAndSortingRepository<AirRankingInfo, Long>, JpaSpecificationExecutor<AirRankingInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    AirRankingInfo findById(Long id);

//    /**
//     * @param userName
//     * @return
//     */
//    AirRankingInfo findByUserName(String userName);

    List<AirRankingInfo> findByUpdateTime(String updateTime);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from AirRankingInfo t where id = :id")
    List<AirRankingInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

    List<AirRankingInfo> findTop1ByOrderByCreateTimeDesc();


}
