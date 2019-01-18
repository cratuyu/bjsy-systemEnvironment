package com.leadmap.environmentalrotection.dto;

import com.leadmap.environmentalrotection.entity.company.CountryMonitorWaterCompanyInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/4 14:31
 */
public class CountryMonitorWaterCompanyInfoContainerDTO implements Serializable {

    public List<CountryMonitorWaterCompanyInfo> countryMonitorWaterCompanyInfos;

    public List<CountryMonitorWaterCompanyInfo> getCountryMonitorWaterCompanyInfos() {
        return countryMonitorWaterCompanyInfos;
    }

    public void setCountryMonitorWaterCompanyInfos(List<CountryMonitorWaterCompanyInfo> countryMonitorWaterCompanyInfos) {
        this.countryMonitorWaterCompanyInfos = countryMonitorWaterCompanyInfos;
    }
}
