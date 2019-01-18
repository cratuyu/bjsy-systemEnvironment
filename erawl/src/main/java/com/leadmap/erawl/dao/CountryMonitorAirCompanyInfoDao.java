package com.leadmap.erawl.dao;

import com.leadmap.erawl.entity.company.CountryMonitorAirCompanyInfo;
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
public interface CountryMonitorAirCompanyInfoDao extends PagingAndSortingRepository<CountryMonitorAirCompanyInfo, Long>, JpaSpecificationExecutor<CountryMonitorAirCompanyInfo> {

    List<CountryMonitorAirCompanyInfo> findByMonitorDate(String monitorDate);

}
