package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.weather.AvenueWeatherInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/18 18:29
 */
@Repository
public interface AvenueWeatherInfoDao extends PagingAndSortingRepository<AvenueWeatherInfo, Long>, JpaSpecificationExecutor<AvenueWeatherInfo> {

    List<AvenueWeatherInfo> findTop1ByOrderByCreateTimeDesc();

    List<AvenueWeatherInfo> findTopByOrderByTimeDesc();

    @Query(value = "select * from avenue_weather_info where avenue_id =?1 and time =?2  ", nativeQuery = true)
    public List<AvenueWeatherInfo> findAllByTime(String avenueId, Date time);
}
