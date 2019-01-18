package com.leadmap.environmentalrotection.dto;

import com.leadmap.environmentalrotection.dto.air.DataContainerDTO;
import com.leadmap.environmentalrotection.entity.company.CountryMonitorAirCompanyInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/4 15:16
 */
public class CountryMonitorAirCompanyInfoContainerDTO implements Serializable {

    public CountryMonitorAirCompanyInfoContainerDTO(){
        aqi = new DataContainerDTO();
    }

    private DataContainerDTO aqi;

    public List<CountryMonitorAirCompanyInfo> countryMonitorAirCompanyInfos;

    private long recordCount;

    private long pageCount;

    public DataContainerDTO getAqi() {
        return aqi;
    }

    public void setAqi(DataContainerDTO aqi) {
        this.aqi = aqi;
    }

    public List<CountryMonitorAirCompanyInfo> getCountryMonitorAirCompanyInfos() {
        return countryMonitorAirCompanyInfos;
    }

    public void setCountryMonitorAirCompanyInfos(List<CountryMonitorAirCompanyInfo> countryMonitorAirCompanyInfos) {
        this.countryMonitorAirCompanyInfos = countryMonitorAirCompanyInfos;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }
}
