package com.leadmap.erawl.common.schedule;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/10 17:47
 */

import com.leadmap.erawl.service.ErawlCountryMonitorAirCompanyInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定时获取企业废气信息
 */
@Configuration
@EnableScheduling
@Component
public class ScheduleRefreshCountryMonitorAirCompanyInfo {

    private final static Logger logger = LoggerFactory.getLogger(ScheduleRefreshCountryMonitorAirCompanyInfo.class);

    @Autowired
    private ErawlCountryMonitorAirCompanyInfoService countryMonitorAirCompanyInfoService;

//    @Scheduled(cron = "0 0 0 1/1 * ?")
    @Transactional(rollbackFor= Exception.class)
    public void scheduleUpdateCronTrigger() {
        try {
            countryMonitorAirCompanyInfoService.generateDataFromSite();
        }catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
