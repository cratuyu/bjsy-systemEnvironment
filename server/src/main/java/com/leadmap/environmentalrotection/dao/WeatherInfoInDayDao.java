package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.weather.WeatherInfoInDay;
import com.leadmap.environmentalrotection.entity.weather.WeatherInfoInDay;
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
public interface WeatherInfoInDayDao extends PagingAndSortingRepository<WeatherInfoInDay, Long>, JpaSpecificationExecutor<WeatherInfoInDay> {

    /**
     * 根据城市id查找
     *
     * @param cityId
     * @return
     */
    List<WeatherInfoInDay> findByCityId(String cityId);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from WeatherInfoInDay t where id = :id")
    List<WeatherInfoInDay> queryFamilyList(@Param("id") Long id, Pageable pageable);

}
