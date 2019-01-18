package com.leadmap.management.util.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Company: www.leadmap.net
 * Description:  定时统计数量
 *
 * @author: yxm
 * @Date: 2018/11/16 13:21
 */
@Configuration
@EnableScheduling
@Component
public class ScheduleIpInfo {

    private final static Logger logger = LoggerFactory.getLogger(ScheduleIpInfo.class);



    @Scheduled(cron = "0 0 0/1 * * ?")
    @Transactional(rollbackFor= Exception.class)
    public void scheduleIpInfo() {
        try {

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
