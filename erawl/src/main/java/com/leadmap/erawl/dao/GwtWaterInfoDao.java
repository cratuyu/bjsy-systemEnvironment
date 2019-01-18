package com.leadmap.erawl.dao;

import com.leadmap.erawl.entity.river.GwtWaterInfo;
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
public interface GwtWaterInfoDao extends PagingAndSortingRepository<GwtWaterInfo, Long>, JpaSpecificationExecutor<GwtWaterInfo> {

}
