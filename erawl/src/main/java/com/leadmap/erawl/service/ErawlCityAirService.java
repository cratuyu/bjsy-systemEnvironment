package com.leadmap.erawl.service;

import com.leadmap.erawl.common.util.Util;
import com.leadmap.erawl.dao.AirQualityInfoDao;
import com.leadmap.erawl.dao.StationInfoDao;
import com.leadmap.erawl.entity.weather.AQI24hInfo;
import com.leadmap.erawl.entity.weather.AirQualityInfo;
import com.leadmap.erawl.entity.weather.StationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/18 10:08
 */
@Component
public class ErawlCityAirService {

    private final static Logger logger = LoggerFactory.getLogger(ErawlCityAirService.class);

    @Value("${cityair.serviceurl}")
    private String serviceUrl;

    @Autowired
    private StationInfoDao stationInfoDao;

    @Autowired
    private AirQualityInfoDao airQualityInfoDao;

    public void generateData() {
        try {
            ErawlAirQualityCrawler airQualityCrawler = new ErawlAirQualityCrawler("crawl", true, this.serviceUrl);
            airQualityCrawler.setThreads(1);
            airQualityCrawler.setTopN(1);
            airQualityCrawler.start(1);
            List<AirQualityInfo> airQualityInfoList = airQualityCrawler.getAirQualityInfoList();
            List<AirQualityInfo> airQualityInfoArrayList = new ArrayList<>();
            for (AirQualityInfo airQualityInfo : airQualityInfoList) {
                for (StationInfo stationInfo : stationInfoDao.findAll()) {
                    if (airQualityInfo.getId().equalsIgnoreCase(stationInfo.getStationNumber())) {
                        airQualityInfo.setStationName(stationInfo.getStationName());
                        airQualityInfo.setCountryStation(stationInfo.isCountryStation());
                        airQualityInfo.setX(stationInfo.getX());
                        airQualityInfo.setY(stationInfo.getY());
                        airQualityInfo.setCreateTime(new Date());
                        airQualityInfoArrayList.add(airQualityInfo);

                        break;
                    }
                }
            }

            for (AirQualityInfo airQualityInfo : airQualityInfoArrayList) {
                for (AQI24hInfo aqi24hInfo : airQualityCrawler.getAqi24hInfoList()) {
                    if (airQualityInfo.getId().equalsIgnoreCase(aqi24hInfo.getStationNumber())) {
                        airQualityInfo.setAqi24h(aqi24hInfo.getAqi24h());
                        break;
                    }
                }
            }

            airQualityInfoDao.delete(airQualityInfoDao.findAll(Util.getDaySpecification(new Date())));
            airQualityInfoDao.save(airQualityInfoArrayList);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
