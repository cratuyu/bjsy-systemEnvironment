package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.river.GwtWaterInfo;
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
public interface GwtWaterInfoDao extends PagingAndSortingRepository<GwtWaterInfo, Long>, JpaSpecificationExecutor<GwtWaterInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    GwtWaterInfo findById(String id);

    List<GwtWaterInfo> findByDateTime(String dateTime);

    List<GwtWaterInfo> findTop1ByOrderByCreateTimeDesc();

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from GwtWaterInfo t where id = :id")
    List<GwtWaterInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);


    @Query(value = "select * from gwt_water_info where site_name =?1 order by id desc limit 30", nativeQuery = true)
    List<GwtWaterInfo> getGwtWaterInfoList(String siteName);

}
