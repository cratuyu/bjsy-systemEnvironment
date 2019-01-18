package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.weather.AvenueWeatherInfoInDay;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/18 20:09
 */
@Repository
public interface AvenueWeatherInfoInDayDao extends PagingAndSortingRepository<AvenueWeatherInfoInDay, Long>, JpaSpecificationExecutor<AvenueWeatherInfoInDay> {
}
