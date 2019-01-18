package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.weather.AQI24hInfo;
import com.leadmap.environmentalrotection.entity.weather.WeatherInfo;
import com.leadmap.environmentalrotection.entity.weather.WeatherInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/5/17 10:12
 */

@Repository
public interface WeatherInfoDao extends PagingAndSortingRepository<WeatherInfo, Long>, JpaSpecificationExecutor<WeatherInfo> {

    List<WeatherInfo> findTop1ByOrderByCreateTimeDesc();

    /**
     * 根据城市名查找
     *
     * @param city
     * @return
     */
    List<WeatherInfo> findByCity(String city);

    /**
     * 根据城市id查找
     *
     * @param cityId
     * @return
     */
    List<WeatherInfo> findByCityId(String cityId);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from WeatherInfo t where id = :id")
    List<WeatherInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

    @Query(value = "select * from WeatherInfo where city_id=?1",nativeQuery = true)
    List<Object> findByCityIdAndCreateTime(String cityId);
}
