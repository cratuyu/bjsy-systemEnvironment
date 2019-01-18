package com.leadmap.erawl.dao;

import com.leadmap.erawl.entity.weather.AvenueInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/18 10:58
 */
@Repository
public interface AvenueInfoDao extends PagingAndSortingRepository<AvenueInfo, Long>, JpaSpecificationExecutor<AvenueInfo> {

    /**
     * 根据街道id查找
     *
     * @param avenueId
     * @return
     */
    AvenueInfo findByAvenueId(String avenueId);

    /**
     * 根据街道名查找
     *
     * @param monitoringSite
     * @return
     */
    AvenueInfo findByMonitoringSite(String monitoringSite);
}
