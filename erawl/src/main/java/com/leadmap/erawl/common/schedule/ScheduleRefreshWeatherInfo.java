package com.leadmap.erawl.common.schedule;

/**
 * Company: www.leadmap.net
 * Description:定时获取生成天气信息
 *
 * @author: ttq
 * @Date: 2018/7/10 17:47
 */

import com.leadmap.erawl.service.ErawlWeatherInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableScheduling
@Component
public class ScheduleRefreshWeatherInfo {

    private final static Logger logger = LoggerFactory.getLogger(ScheduleRefreshWeatherInfo.class);



    @Autowired
    private ErawlWeatherInfoService weatherInfoService;

//    @Scheduled(cron = "0 0 6,12,18 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void scheduleUpdateCronTrigger() {
        try{
            weatherInfoService.generateData();
        }catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
