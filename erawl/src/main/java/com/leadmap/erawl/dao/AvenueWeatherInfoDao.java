package com.leadmap.erawl.dao;

import com.leadmap.erawl.entity.weather.AvenueWeatherInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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
}
