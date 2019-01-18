package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.company.CountryMonitorAirCompanyInfo;
import com.leadmap.environmentalrotection.entity.company.CountryMonitorWaterCompanyInfo;
import com.leadmap.environmentalrotection.entity.river.GwtWaterInfo;
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
public interface CountryMonitorAirCompanyInfoDao extends PagingAndSortingRepository<CountryMonitorAirCompanyInfo, Long>, JpaSpecificationExecutor<CountryMonitorAirCompanyInfo> {

    /**
     * 根据id查找
     * @param id
     * @return
     */
    CountryMonitorAirCompanyInfo findById(String id);

    List<CountryMonitorAirCompanyInfo> findByMonitorDate(String monitorDate);

    List<CountryMonitorAirCompanyInfo> findTop1ByOrderByCreateTimeDesc();

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from CountryMonitorAirCompanyInfo t where id = :id")
    List<CountryMonitorAirCompanyInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

}
