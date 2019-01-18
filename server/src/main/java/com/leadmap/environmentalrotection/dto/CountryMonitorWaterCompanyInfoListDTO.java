package com.leadmap.environmentalrotection.dto;

import com.leadmap.environmentalrotection.dto.air.DataContainerDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/11/20 13:17
 */
public class CountryMonitorWaterCompanyInfoListDTO implements Serializable {

    public CountryMonitorWaterCompanyInfoListDTO(){
        wwg = new DataContainerDTO();
    }

    private DataContainerDTO wwg;

    public List<CountryMonitorWaterCompanyInfoDTO> countryMonitorWaterCompanyInfoDTOS;

    public DataContainerDTO getWwg() {
        return wwg;
    }

    public void setWwg(DataContainerDTO wwg) {
        this.wwg = wwg;
    }

    public List<CountryMonitorWaterCompanyInfoDTO> getCountryMonitorWaterCompanyInfoDTOS() {
        return countryMonitorWaterCompanyInfoDTOS;
    }

    public void setCountryMonitorWaterCompanyInfoDTOS(List<CountryMonitorWaterCompanyInfoDTO> countryMonitorWaterCompanyInfoDTOS) {
        this.countryMonitorWaterCompanyInfoDTOS = countryMonitorWaterCompanyInfoDTOS;
    }
}
