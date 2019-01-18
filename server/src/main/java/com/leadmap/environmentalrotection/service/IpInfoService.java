package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dao.IpInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/9 14:45
 */
@Component
public class IpInfoService {

    private final static Logger logger = LoggerFactory.getLogger(IpInfoService.class);

    @Autowired
    private IpInfoDao ipInfoDao;

    /**
     * 添加游客ip信息
     * @param ip
     * @throws ParseException
     */
    public void addTouristIpInfo(String ip) throws ParseException {
       try {
           String date = Util.getStrDayFromDate(new Date());
           ipInfoDao.insertIpInfo(ip ,date);
       }catch (Exception ex) {
           logger.error(ex.getMessage(), ex);
       }

        //判断ip是否存在
//        List<IpInfo> ipInfo = ipInfoDao.findByIp(ip,1 ,date);
//        if (ipInfo != null && ipInfo.size() > 0){
//            ipInfoDao.updateIpInfo(ip ,date );
//        }else {
//            ipInfoDao.insertIpInfo(ip ,date);
//        }
    }

    /**
     * 添加注册用户ip信息
     * @param ip
     */
    public void addRegisteredUserIpInfo(String ip,String userAgent,String userName) throws ParseException {
        try {
            String date = Util.getStrDayFromDate(new Date());
            ipInfoDao.insertRegisteredUserIpInfo(ip ,date ,userAgent ,userName);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        //判断ip是否存在
//        List<IpInfo> ipInfo = ipInfoDao.findByIpAndFlag(ip,2 ,date ,userName);
//        if (ipInfo != null && ipInfo.size() > 0){
//            ipInfoDao.updateRegisteredUserIpInfo(ip ,date ,userAgent ,userName);
//        }else {
//            ipInfoDao.insertRegisteredUserIpInfo(ip ,date ,userAgent ,userName);
//        }
    }



}
