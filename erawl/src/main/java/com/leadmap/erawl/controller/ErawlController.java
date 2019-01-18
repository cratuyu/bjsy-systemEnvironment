package com.leadmap.erawl.controller;

import com.leadmap.erawl.service.*;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/11/9 14:56
 */
@RestController
public class ErawlController {

    private final static Logger logger = LoggerFactory.getLogger(ErawlController.class);

    @Autowired
    private ErawlCityAirService cityAirService;

    @Autowired
    private ErawlAirRankingInfoService airRankingInfoService;

    @Autowired
    private ErawlCountryMonitorAirCompanyInfoService countryMonitorAirCompanyInfoService;

    @Autowired
    private ErawlCountryMonitorWaterCompanyInfoService countryMonitorWaterCompanyInfoService;

    @Autowired
    private ErawlFairsenseService fairsenseService;

    @Autowired
    private ErawlGwtWaterInfoService gwtWaterInfoService;

    @Autowired
    private ErawlWeatherInfoService weatherInfoService;

    @Autowired
    private ErawlWeatherAvenueInfoService weatherAvenueInfoService;

    @ApiOperation(value = "获取生成城市空气质量信息", notes = "获取生成城市空气质量信息")
    @RequestMapping(value = "getScheduleRefreshAirQualityInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String getScheduleRefreshAirQualityInfo() {
        try {
            cityAirService.generateData();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }


    @ApiOperation(value = "获取全国空气质量实时排名", notes = "获取全国空气质量实时排名")
    @RequestMapping(value = "getScheduleRefreshAirRankingInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String getScheduleRefreshAirRankingInfo() {
        try {
            airRankingInfoService.generateData();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @ApiOperation(value = "获取企业废水信息", notes = "获取企业废水信息")
    @RequestMapping(value = "getScheduleRefreshCountryMonitorAirCompanyInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String getScheduleRefreshCountryMonitorAirCompanyInfo() {
        try {
            countryMonitorAirCompanyInfoService.generateDataFromSite();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @ApiOperation(value = "获取企业废水信息", notes = "获取企业废水信息")
    @RequestMapping(value = "getScheduleRefreshCountryMonitorWaterCompanyInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String getScheduleRefreshCountryMonitorWaterCompanyInfo() {
        try {
            countryMonitorWaterCompanyInfoService.generateDataFromSite();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }


    @ApiOperation(value = "定时获取微站监测数据", notes = "定时获取微站监测数据")
    @RequestMapping(value = "getScheduleRefreshFairsenseMonitorInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String getScheduleRefreshFairsenseMonitorInfo() {
        try {
            fairsenseService.generateAllStationsRealTimeData();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @ApiOperation(value = "定时获取地表水水质信息", notes = "定时获取地表水水质信息")
    @RequestMapping(value = "getScheduleRefreshGwtWaterInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String getScheduleRefreshGwtWaterInfo() {
        try {
            gwtWaterInfoService.generateGwtWaterInfoFromSiteServer();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @ApiOperation(value = "定时获取顺义区街道天气信息", notes = "定时获取顺义区街道天气信息")
    @RequestMapping(value = "getScheduleRefreshWeatherAvenueInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String getScheduleRefreshWeatherAvenueInfo() {
        try {
            weatherAvenueInfoService.getAvenueWeatherData();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @ApiOperation(value = "定时获取生成天气信息", notes = "定时获取生成天气信息")
    @RequestMapping(value = "getScheduleRefreshWeatherInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String getScheduleRefreshWeatherInfo() {
        try {
            weatherInfoService.generateData();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

}
