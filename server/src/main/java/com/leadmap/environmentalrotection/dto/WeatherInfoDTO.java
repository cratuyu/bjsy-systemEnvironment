package com.leadmap.environmentalrotection.dto;

import com.leadmap.environmentalrotection.entity.weather.AvenueWeatherInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/22 9:34
 */
public class WeatherInfoDTO implements Serializable {


    private List<AvenueWeatherInfo> avenueWeatherInfoList;

    public List<AvenueWeatherInfo> getAvenueWeatherInfoList() {
        return avenueWeatherInfoList;
    }

    public void setAvenueWeatherInfoList(List<AvenueWeatherInfo> avenueWeatherInfoList) {
        this.avenueWeatherInfoList = avenueWeatherInfoList;
    }

}
