package com.leadmap.erawl.dao;

import com.leadmap.erawl.entity.weather.StationInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/5/17 10:12
 */

@Repository
public interface StationInfoDao extends PagingAndSortingRepository<StationInfo, Long>, JpaSpecificationExecutor<StationInfo> {

}
