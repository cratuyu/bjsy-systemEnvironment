package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.weather.WeatherCityInfo;
import com.leadmap.environmentalrotection.entity.weather.WeatherInfo;
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
public interface WeatherCityInfoDao extends PagingAndSortingRepository<WeatherCityInfo, Long>, JpaSpecificationExecutor<WeatherCityInfo> {

    /**
     * 根据城市名查找
     *
     * @param cityName
     * @return
     */
    WeatherCityInfo findByCityName(String cityName);

    /**
     * 根据城市id查找
     *
     * @param cityId
     * @return
     */
    WeatherCityInfo findByCityId(String cityId);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from WeatherCityInfo t where id = :id")
    List<WeatherCityInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

}
