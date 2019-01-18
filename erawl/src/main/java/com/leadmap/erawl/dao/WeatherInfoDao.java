package com.leadmap.erawl.dao;

import com.leadmap.erawl.entity.weather.WeatherInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
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
public interface WeatherInfoDao extends PagingAndSortingRepository<WeatherInfo, Long>, JpaSpecificationExecutor<WeatherInfo> {


    /**
     * 根据城市id查找
     *
     * @param cityId
     * @return
     */
    List<WeatherInfo> findByCityId(String cityId);


}
