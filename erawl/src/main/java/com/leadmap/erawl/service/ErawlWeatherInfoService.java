package com.leadmap.erawl.service;

import com.leadmap.erawl.dao.WeatherCityInfoDao;
import com.leadmap.erawl.dao.WeatherInfoDao;
import com.leadmap.erawl.dao.WeatherInfoInDayDao;
import com.leadmap.erawl.entity.weather.WeatherCityInfo;
import com.leadmap.erawl.entity.weather.WeatherInfo;
import com.leadmap.erawl.entity.weather.WeatherInfoInDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/18 16:41
 */
@Component
public class ErawlWeatherInfoService {

    private final static Logger logger = LoggerFactory.getLogger(ErawlWeatherInfoService.class);

    private Logger logger1 = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeatherInfoDao weatherInfoDao;

    @Autowired
    private WeatherInfoInDayDao weatherInfoInDayDao;

    @Autowired
    private ErawlWeatherCrawler weatherCrawler;

    @Autowired
    private WeatherCityInfoDao weatherCityInfoDao;

    public void generateData() {
        try {
            List<WeatherInfo> weatherInfoList = new ArrayList<>();
            List<WeatherInfoInDay> weatherInfoInDayList = new ArrayList<>();

            for (WeatherCityInfo weatherCityInfo : weatherCityInfoDao.findAll()) {
                WeatherInfo weatherInfo = weatherCrawler.generateTodayWeaterInfo(weatherCityInfo.getCityId());
                if (weatherInfo != null) {
                    weatherInfoList.add(weatherInfo);
                }

                List<WeatherInfoInDay> weatherInfoInDayList1 = weatherCrawler.generate7DayWeaterInfo(weatherCityInfo.getCityId());
                if (weatherInfoInDayList1 != null && weatherInfoInDayList1.size() > 0) {
                    weatherInfoInDayList.addAll(weatherInfoInDayList1);
                }

                List<WeatherInfoInDay> weatherInfoInDayList2 = weatherCrawler.generate15DayWeaterInfo(weatherCityInfo.getCityId());
                if (weatherInfoInDayList2 != null && weatherInfoInDayList2.size() > 0) {
                    weatherInfoInDayList.addAll(weatherInfoInDayList2);
                }
            }
            logger1.info("获取weatherInfoList : " +  weatherInfoList);
//            weatherInfoDao.delete(weatherInfoDao.findAll(Util.getDaySpecification(new Date())));
            weatherInfoDao.save(weatherInfoList);
            logger1.info("获取weatherInfoList : " +  weatherInfoInDayList);
//            weatherInfoInDayDao.delete(weatherInfoInDayDao.findAll(Util.getDaySpecification(new Date())));
            weatherInfoInDayDao.save(weatherInfoInDayList);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
