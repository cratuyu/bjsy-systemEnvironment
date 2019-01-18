package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.dao.WeatherCityInfoDao;
import com.leadmap.environmentalrotection.entity.weather.WeatherCityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/18 16:08
 */
@Component
public class WeatherCityInfoService {
    @Autowired
    private WeatherCityInfoDao weatherCityInfoDao;

    public List<WeatherCityInfo> getWeatherCityInfos() {
        List<WeatherCityInfo> weatherCityInfos = new ArrayList<>();
        Iterable<WeatherCityInfo> weatherCityInfoIterable = weatherCityInfoDao.findAll();
        for (WeatherCityInfo gwtWaterInfo : weatherCityInfoIterable) {
            weatherCityInfos.add(gwtWaterInfo);
        }
        return weatherCityInfos;
    }
}
