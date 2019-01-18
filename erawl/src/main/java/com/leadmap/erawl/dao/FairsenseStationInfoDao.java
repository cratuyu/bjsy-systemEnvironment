package com.leadmap.erawl.dao;

import com.leadmap.erawl.entity.fairsense.FairsenseStationInfo;
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
public interface FairsenseStationInfoDao extends PagingAndSortingRepository<FairsenseStationInfo, Long>, JpaSpecificationExecutor<FairsenseStationInfo> {

}
