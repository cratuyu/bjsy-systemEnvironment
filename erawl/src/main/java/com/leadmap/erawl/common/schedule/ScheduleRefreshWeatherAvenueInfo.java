package com.leadmap.erawl.common.schedule;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/10 17:47
 */

import com.leadmap.erawl.service.ErawlWeatherAvenueInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定时获取顺义区街道天气信息
 */
@Configuration
@EnableScheduling
@Component
public class ScheduleRefreshWeatherAvenueInfo {

    private final static Logger logger = LoggerFactory.getLogger(ScheduleRefreshWeatherAvenueInfo.class);

    @Autowired
    private ErawlWeatherAvenueInfoService weatherAvenueInfoService;

    @Scheduled(cron = "0 0 6,12,18 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void scheduleUpdateCronTrigger() {
        try{
            weatherAvenueInfoService.getAvenueWeatherData();
        }catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
