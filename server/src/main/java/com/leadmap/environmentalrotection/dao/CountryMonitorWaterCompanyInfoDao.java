package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.company.CountryMonitorWaterCompanyInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/5/17 10:12
 */

@Repository
public interface CountryMonitorWaterCompanyInfoDao extends PagingAndSortingRepository<CountryMonitorWaterCompanyInfo, Long>, JpaSpecificationExecutor<CountryMonitorWaterCompanyInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    CountryMonitorWaterCompanyInfo findById(String id);

    List<CountryMonitorWaterCompanyInfo> findByMonitorDate(String monitorDate);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from CountryMonitorWaterCompanyInfo t where id = :id")
    List<CountryMonitorWaterCompanyInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

    List<CountryMonitorWaterCompanyInfo> findTop1ByOrderByCreateTimeDesc();

    @Query(value = "select * from country_monitor_water_company_info where create_time =?1 and company_name =?2 and monitor_name=?3", nativeQuery = true)
    List<CountryMonitorWaterCompanyInfo> getCountryMonitorWaterInfoList(Date createTime, String companyName ,String monitorName);

    @Query(value = "select * from country_monitor_water_company_info where company_name =?1 and monitor_name=?2 order by id desc limit 1", nativeQuery = true)
    List<CountryMonitorWaterCompanyInfo> getCountryMonitorWaterCompanyInfo30d(String companyName,String monitorName);

}
