package com.leadmap.erawl.service;

import com.leadmap.erawl.common.util.Util;
import com.leadmap.erawl.dao.AvenueInfoDao;
import com.leadmap.erawl.dao.AvenueWeatherInfoDao;
import com.leadmap.erawl.dao.AvenueWeatherInfoInDayDao;
import com.leadmap.erawl.entity.weather.AvenueInfo;
import com.leadmap.erawl.entity.weather.AvenueWeatherInfo;
import com.leadmap.erawl.entity.weather.AvenueWeatherInfoInDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/17 18:46
 */
@Component
public class ErawlWeatherAvenueInfoService {

    private final static Logger logger = LoggerFactory.getLogger(ErawlWeatherAvenueInfoService.class);

    private Logger logger1 = LoggerFactory.getLogger(getClass());

    @Value("${shunYiAvenueUrl}")
    private String shunYiAvenueUrl;

    @Autowired
    private AvenueInfoDao avenueInfoDao;

    @Autowired
    private AvenueWeatherInfoDao avenueWeatherInfoDao;

    @Autowired
    private AvenueWeatherInfoInDayDao avenueWeatherInfoInDayDao;

    @Autowired
    private ErawlAvenueWeatherCrawler avenueWeatherCrawler;

    public void getAvenueWeatherData() {
        try {
            List<AvenueWeatherInfo> avenueWeatherInfoList = new ArrayList<>();
            List<AvenueWeatherInfoInDay> avenueWeatherInfoInDayList = new ArrayList<>();
            for (AvenueInfo avenueInfo : avenueInfoDao.findAll()) {
                String updateTime = Util.getTodayDate();
                AvenueWeatherInfo avenueWeatherInfo = avenueWeatherCrawler.getTodayAvenueWeaterInfo(avenueInfo.getAvenueId(),updateTime);
                if (avenueWeatherInfo != null) {
                    avenueWeatherInfoList.add(avenueWeatherInfo);
                }

                List<AvenueWeatherInfoInDay> avenueWeatherInfoInDayList1 = avenueWeatherCrawler.get7DayAvenueWeatherInfo(avenueInfo.getAvenueId());
                if (avenueWeatherInfoInDayList1 != null && avenueWeatherInfoInDayList1.size() > 0) {
                    avenueWeatherInfoInDayList.addAll(avenueWeatherInfoInDayList1);
                }

                List<AvenueWeatherInfoInDay> avenueWeatherInfoInDayList2 = avenueWeatherCrawler.get15DayAvenueWeatherInfo(avenueInfo.getAvenueId());
                if (avenueWeatherInfoInDayList1 != null && avenueWeatherInfoInDayList1.size() > 0) {
                    avenueWeatherInfoInDayList.addAll(avenueWeatherInfoInDayList2);
                }
            }
            avenueWeatherInfoDao.save(avenueWeatherInfoList);
            avenueWeatherInfoInDayDao.save(avenueWeatherInfoInDayList);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }



}
