package com.leadmap.environmentalrotection.web.controller;

import com.leadmap.environmentalrotection.common.util.GetIpUtil;
import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dao.AvenueWeatherInfoDao;
import com.leadmap.environmentalrotection.dao.WeatherCityInfoDao;
import com.leadmap.environmentalrotection.dto.*;
import com.leadmap.environmentalrotection.dto.air.CityAirInfoDTO;
import com.leadmap.environmentalrotection.entity.air.AirIndicatorInfo;
import com.leadmap.environmentalrotection.entity.air.AirRankingInfo;
import com.leadmap.environmentalrotection.entity.company.CountryMonitorWaterCompanyInfo;
import com.leadmap.environmentalrotection.entity.company.DrainContaminationCompanyInfo;
import com.leadmap.environmentalrotection.entity.company.DrainContaminationCompanyTypes;
import com.leadmap.environmentalrotection.entity.river.GwtWaterInfo;
import com.leadmap.environmentalrotection.entity.river.RiverWaterQualityInfo;
import com.leadmap.environmentalrotection.entity.river.WaterQualityReservoirInfo;
import com.leadmap.environmentalrotection.entity.weather.*;
import com.leadmap.environmentalrotection.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/5/18 11:26
 */
@RestController
public class AirController {

    private final static Logger logger = LoggerFactory.getLogger(AirController.class);

    @Autowired
    private CityAirService cityAirService;

    @Autowired
    private WeatherCityInfoDao weatherCityInfoDao;

    @Autowired
    private GwtWaterInfoService gwtWaterInfoService;


    @Autowired
    private AirRankingInfoService airRankingInfoService;

    @Autowired
    private RiverWaterQualityInfoService riverWaterQualityInfoService;

    @Autowired
    private WaterQualityReservoirInfoService waterQualityReservoirInfoService;

    @Autowired
    private DrainContaminationCompanyInfoService drainContaminationCompanyInfoService;

    @Autowired
    private CountryMonitorWaterCompanyInfoService countryMonitorWaterCompanyInfoService;

    @Autowired
    private CountryMonitorAirCompanyInfoService countryMonitorAirCompanyInfoService;

    @Autowired
    private WeatherCityInfoService weatherCityInfoService;

    @Autowired
    private WeatherInfoService weatherInfoService;

    @Autowired
    private WeatherAvenueInfoService weatherAvenueInfoService;

    @Autowired
    private AvenueInfoService avenueInfoService;

    @Autowired
    private IpInfoService ipInfoService;

    @Autowired
    private AvenueWeatherInfoDao avenueWeatherInfoDao;

    @Value("${gdApiUrl}")
    private String gdApiUrl;

    @Value("${gdApiKey}")
    private String gdApiKey;

    /**
     * 获取城市空气
     */
    @ApiOperation(value = "获取城市空气", notes = "获取城市空气")
    @RequestMapping(value = "getCityAir", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryDate", value = "queryDate", required = false, dataType = "java.util.Date", paramType = "query", defaultValue = "")
    })
    @ResponseBody
    @Transactional
    public ResultObjInfo<CityAirInfoDTO> getCityAir(ServletRequest request) {
        ResultObjInfo<CityAirInfoDTO> resultInfo = new ResultObjInfo<>();
        try {
            String strQueryDate = request.getParameter("queryDate");
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (StringUtils.isBlank(strQueryDate)) {
                String strDate = Util.getStrDayFromDate(cityAirService.getNewestDate());
                date = dateFormat.parse(strDate);
            } else {
                date = dateFormat.parse(strQueryDate);
            }
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            CityAirInfoDTO cityAirInfoDTO = cityAirService.getCityAirInfo(date);
            resultInfo.setData(cityAirInfoDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
        }

        return resultInfo;
    }


    /**
     * 获取街道天气信息
     */
    @ApiOperation(value = "获取当天街道天气信息", notes = "获取当天街道天气信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x", value = "x", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "y", value = "y", required = true, dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "getAvenueWeatherInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public Map<String, Object> getAvenueWeatherInfo(ServletRequest request) {

        String avenueWeatherImagesUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/weatherImages/";
        Map<String, Object> map = new HashMap<>();
        WeatherInfoDTO weatherInfoDTO = new WeatherInfoDTO();
        try {
            //获取访问者ip
            String ip = GetIpUtil.getIp((HttpServletRequest) request);
            ipInfoService.addTouristIpInfo(ip);

            String x = request.getParameter("x");
            String y = request.getParameter("y");
            if (StringUtils.isBlank(x) && StringUtils.isBlank(y)) {
                throw new IllegalArgumentException();
            }
            String parameter = "key=" + gdApiKey + "&location=" + x + "," + y + "&poitype=&radius=&extensions=base&batch=false&roadlevel=1";
            //获取街道id
            String avenueId = weatherAvenueInfoService.getCoordinateAddress(gdApiUrl, parameter);

            List<AvenueWeatherInfo> list = avenueWeatherInfoDao.findTopByOrderByTimeDesc();
            Date dates = new Date();
            Date date = new Date();
            String defaultId = null;
            String id ;
            //顺义区街道id
            String str = "101010400018";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (list != null && list.size() > 0) {
                date = list.get(0).getTime();
                defaultId = list.get(0).getAvenueId();
                String strDate = Util.getStrDayYYMMddHHMMSS(date);
                dates = dateFormat.parse(strDate);
            }
            //判断 街道id是否存在
            List<AvenueWeatherInfo> list1 = avenueWeatherInfoDao.findAllByTime(avenueId , date);
            if (list1 != null && list1.size() > 0){
                id = avenueId;
                AvenueWeatherInfo avenueWeatherInfo = weatherAvenueInfoService.getAvenueWeatherInfo(id, avenueWeatherImagesUrl, dates);
                List<AvenueWeatherInfo> avenueWeatherInfoList = new ArrayList<>();
                avenueWeatherInfoList.add(avenueWeatherInfo);
                weatherInfoDTO.setAvenueWeatherInfoList(avenueWeatherInfoList);
                map.put("data", weatherInfoDTO);
            }else {
                List<AvenueWeatherInfo> list2 = avenueWeatherInfoDao.findAllByTime(str , date);
                if (list2 != null && list2.size() > 0){
                    id = str;
                }else {
                    id = defaultId;
                }
                AvenueWeatherInfo avenueWeatherInfo = weatherAvenueInfoService.getAvenueWeatherInfo(id, avenueWeatherImagesUrl, dates);
                AvenueWeatherInfo avenueWeatherInfo1 = new AvenueWeatherInfo();
                avenueWeatherInfo1.setId(avenueWeatherInfo.getId());
                avenueWeatherInfo1.setCreateTime(avenueWeatherInfo.getCreateTime());
                avenueWeatherInfo1.setTime(avenueWeatherInfo.getTime());
                avenueWeatherInfo1.setStrTime(avenueWeatherInfo.getStrTime());
                avenueWeatherInfo1.setAvenue("顺义");
                avenueWeatherInfo1.setAvenueId("101010400");
                avenueWeatherInfo1.setMaxTemperature(avenueWeatherInfo.getMaxTemperature());
                avenueWeatherInfo1.setMinTemperature(avenueWeatherInfo.getMinTemperature());
                avenueWeatherInfo1.setCurrentTemperature(avenueWeatherInfo.getCurrentTemperature());
                avenueWeatherInfo1.setHumidity(avenueWeatherInfo.getHumidity());
                avenueWeatherInfo1.setWindDirection(avenueWeatherInfo.getWindDirection());
                avenueWeatherInfo1.setWindPower(avenueWeatherInfo.getWindPower());
                avenueWeatherInfo1.setPrecipitation(avenueWeatherInfo.getPrecipitation());
                avenueWeatherInfo1.setLifeIndex(avenueWeatherInfo.getLifeIndex());

                List<AvenueWeatherInfoInHour> weatherInfoInHour = avenueWeatherInfo.getAvenueWeatherInfoInHourList();
                List<AvenueWeatherInfoInHour> avenueWeatherInfoList = new ArrayList<>();
                for (AvenueWeatherInfoInHour weatherInfoInHour1 : weatherInfoInHour) {
                    AvenueWeatherInfoInHour avenueWeatherInfoInHour = new AvenueWeatherInfoInHour();
                    avenueWeatherInfoInHour.setId(weatherInfoInHour1.getId());
                    avenueWeatherInfoInHour.setCreateTime(weatherInfoInHour1.getCreateTime());
                    avenueWeatherInfoInHour.setHour(weatherInfoInHour1.getHour());
                    avenueWeatherInfoInHour.setTemperature(weatherInfoInHour1.getTemperature());
                    avenueWeatherInfoInHour.setWindDirection(weatherInfoInHour1.getWindDirection());
                    avenueWeatherInfoInHour.setWindPower(weatherInfoInHour1.getWindPower());
                    avenueWeatherInfoInHour.setWeatherStatus(weatherInfoInHour1.getWeatherStatus());
                    avenueWeatherInfoInHour.setWeatherStatusImgUrl(weatherInfoInHour1.getWeatherStatusImgUrl());
                    avenueWeatherInfoList.add(avenueWeatherInfoInHour);
                }
                avenueWeatherInfo1.setAvenueWeatherInfoInHourList(avenueWeatherInfoList);

                avenueWeatherInfo1.setEnvironmentalAssessment(avenueWeatherInfo.getEnvironmentalAssessment());
                avenueWeatherInfo1.setHealthGuidelines(avenueWeatherInfo.getHealthGuidelines());
                avenueWeatherInfo1.setCityRankings(avenueWeatherInfo.getCityRankings());

                List<AvenueWeatherInfoInDay> weatherInfo15DayList = avenueWeatherInfo.getAvenueWeatherInfo15DayList();
                List<AvenueWeatherInfoInDay> avenueWeatherInfoInDayList = new ArrayList<>();
                for (AvenueWeatherInfoInDay weatherInfoInDay : weatherInfo15DayList) {
                    AvenueWeatherInfoInDay avenueWeatherInfoInDay = new AvenueWeatherInfoInDay();
                    avenueWeatherInfoInDay.setId(weatherInfoInDay.getId());
                    avenueWeatherInfoInDay.setCreateTime(weatherInfoInDay.getCreateTime());
                    avenueWeatherInfoInDay.setAvenueId("101010400");
                    avenueWeatherInfoInDay.setDay(weatherInfoInDay.getDay());
                    avenueWeatherInfoInDay.setMaxTemperature(weatherInfoInDay.getMaxTemperature());
                    avenueWeatherInfoInDay.setMinTemperature(weatherInfoInDay.getMinTemperature());
                    avenueWeatherInfoInDay.setWindDirection(weatherInfoInDay.getWindDirection());
                    avenueWeatherInfoInDay.setWindPower(weatherInfoInDay.getWindPower());
                    avenueWeatherInfoInDay.setWeatherStatus(weatherInfoInDay.getWeatherStatus());
                    avenueWeatherInfoInDayList.add(avenueWeatherInfoInDay);
                }
                avenueWeatherInfo1.setAvenueWeatherInfo15DayList(avenueWeatherInfoInDayList);

                avenueWeatherInfo1.setWeatherStatus(avenueWeatherInfo.getWeatherStatus());
                avenueWeatherInfo1.setUltravioletRays(avenueWeatherInfo.getUltravioletRays());
                avenueWeatherInfo1.setUltravioletRaysLevel(avenueWeatherInfo.getUltravioletRaysLevel());
                avenueWeatherInfo1.setCold(avenueWeatherInfo.getCold());
                avenueWeatherInfo1.setColdLevel(avenueWeatherInfo.getColdLevel());
                avenueWeatherInfo1.setDress(avenueWeatherInfo.getDress());
                avenueWeatherInfo1.setDressLevel(avenueWeatherInfo.getDressLevel());
                avenueWeatherInfo1.setCarWash(avenueWeatherInfo.getCarWash());
                avenueWeatherInfo1.setCarWashLevel(avenueWeatherInfo.getCarWashLevel());
                avenueWeatherInfo1.setMotion(avenueWeatherInfo.getMotion());
                avenueWeatherInfo1.setMotionLevel(avenueWeatherInfo.getMotionLevel());
                avenueWeatherInfo1.setAirPollutionDispersion(avenueWeatherInfo.getAirPollutionDispersion());
                avenueWeatherInfo1.setAirPollutionDispersionLevel(avenueWeatherInfo.getAirPollutionDispersionLevel());
                avenueWeatherInfo1.setAQI(avenueWeatherInfo.getAQI());

                List<AvenueWeatherInfo> avenueWeatherInfoList1 = new ArrayList<>();
                avenueWeatherInfoList1.add(avenueWeatherInfo1);
                weatherInfoDTO.setAvenueWeatherInfoList(avenueWeatherInfoList1);
                map.put("data", weatherInfoDTO);
            }
            map.put("code", 200);
            map.put("msg", "success");
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            map.put("code", 500);
            map.put("msg", "failure");
            map.put("data", "");
        }
        return map;
    }


    /**
     * 获取当天天气信息
     */
    @ApiOperation(value = "获取当天天气信息", notes = "获取当天天气信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cityId", value = "cityId", required = true, dataType = "string", paramType = "query", defaultValue = "101010100"),
            @ApiImplicitParam(name = "queryDate", value = "queryDate", required = false, dataType = "java.util.Date", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getWeatherInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<WeatherInfo> getWeatherInfo(ServletRequest request) {

        String weatherImagesRootUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/weatherImages/";

        ResultObjInfo<WeatherInfo> resultInfo = new ResultObjInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            String cityId = request.getParameter("cityId");
            if (StringUtils.isBlank(cityId)) {
                throw new IllegalArgumentException();
            }

            String strQueryDate = request.getParameter("queryDate");
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (StringUtils.isBlank(strQueryDate)) {
                String strDate = Util.getStrDayFromDate(weatherInfoService.getNewestDate());
                date = dateFormat.parse(strDate);
            } else {
                date = dateFormat.parse(strQueryDate);
            }

            resultInfo.setData(weatherInfoService.getWeatherInfo(cityId, weatherImagesRootUrl, date));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
        }

        return resultInfo;
    }

    /**
     * 获取河流水质信息
     *
     * @return
     */
    @ApiOperation(value = "获取河流水质信息", notes = "获取河流水质信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getRiverWaterQualityInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<RiverWaterQualityInfo> getRiverWaterQualityInfos(ServletRequest request) {
        ResultInfo<RiverWaterQualityInfo> resultInfo = new ResultInfo<>();
        try {
            String pageNumber = request.getParameter("pageNumber");
            String pageSize = request.getParameter("pageSize");
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setData(riverWaterQualityInfoService.getRiverWaterQualityInfo(pageNumber, pageSize));
            resultInfo.setRecordCount(riverWaterQualityInfoService.getRiverWaterQualityInfoCount());
            resultInfo.setPageCount(Util.getPageCount(resultInfo.getRecordCount(), pageSize));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }
        return resultInfo;
    }

    /**
     * 获取饮用水源信息
     *
     * @return
     */
    @ApiOperation(value = "获取饮用水源信息", notes = "获取饮用水源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getWaterQualityReservoirInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<WaterQualityReservoirInfo> getWaterQualityReservoirInfos(ServletRequest request) {
        ResultInfo<WaterQualityReservoirInfo> resultInfo = new ResultInfo<>();
        try {
            String pageNumber = request.getParameter("pageNumber");
            String pageSize = request.getParameter("pageSize");
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setData(waterQualityReservoirInfoService.getWaterQualityReservoirInfo(pageNumber, pageSize));
            resultInfo.setRecordCount(waterQualityReservoirInfoService.getWaterQualityReservoirInfoCount());
            resultInfo.setPageCount(Util.getPageCount(resultInfo.getRecordCount(), pageSize));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }

        return resultInfo;
    }


    /**
     * 获取水质列表
     *
     * @return
     */
    @ApiOperation(value = "获取水质列表", notes = "获取水质列表")
    @RequestMapping(value = "getGwtWaterInfoList", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<GwtWaterInfoListDTO> getGwtWaterInfoList(ServletRequest request) {
        ResultObjInfo<GwtWaterInfoListDTO> resultObjInfo = new ResultObjInfo<>();
        try {
            GwtWaterInfoListDTO gwtWaterInfoListDTO = new GwtWaterInfoListDTO();

            String strQueryDate = request.getParameter("queryDate");
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (StringUtils.isBlank(strQueryDate)) {
                date = gwtWaterInfoService.getNewestDate();
            } else {
                date = dateFormat.parse(strQueryDate);
                date = gwtWaterInfoService.getNewestInSpecialDay(date);
            }

            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");

            gwtWaterInfoListDTO.getGwt().setAirIndicatorInfoList(cityAirService.getAirIndicatorByName("gwt"));
            gwtWaterInfoListDTO.setGwtWaterInfoList(gwtWaterInfoService.getGwtWaterInfoList(date));
            resultObjInfo.setData(gwtWaterInfoListDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    /**
     * 获取地表水水质信息
     *
     * @return
     */
    @ApiOperation(value = "获取地表水水质信息", notes = "获取地表水水质信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stationName", value = "stationName", required = false, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getGwtWaterInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<GwtWaterInfoContainerDTO> getGwtWaterInfos(ServletRequest request) {
        ResultObjInfo<GwtWaterInfoContainerDTO> resultObjInfo = new ResultObjInfo<>();
        try {

            GwtWaterInfoContainerDTO gwtWaterInfoContainerDTO = new GwtWaterInfoContainerDTO();

            String stationName = request.getParameter("stationName");
            if (StringUtils.isBlank(stationName)){
                resultObjInfo.setCode("500");
                resultObjInfo.setMsg("站点名字不能为空！");
            }else {
                String strQueryDate = request.getParameter("queryDate");
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (StringUtils.isBlank(strQueryDate)) {
                    date = gwtWaterInfoService.getNewestDate();
                } else {
                    date = dateFormat.parse(strQueryDate);
                    date = gwtWaterInfoService.getNewestInSpecialDay(date);
                }
                resultObjInfo.setCode("200");
                resultObjInfo.setMsg("success");

                List<GwtWaterInfo> gwtWaterInfoList = gwtWaterInfoService.getGwtWaterInfo30d(stationName, date);
                Collections.reverse(gwtWaterInfoList);
                gwtWaterInfoContainerDTO.setGwtWaterInfoList(gwtWaterInfoList);
                resultObjInfo.setData(gwtWaterInfoContainerDTO);
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    /**
     * 获取顺义区（大气+水环境）环境重点排污单位名录
     *
     * @return
     */
    @ApiOperation(value = "获取顺义区（大气+水环境）环境重点排污单位名录", notes = "获取顺义区（大气+水环境）环境重点排污单位名录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "type", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getDrainContaminationCompanyInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<DrainContaminationCompanyInfo> getDrainContaminationCompanyInfos(ServletRequest request) {
        ResultInfo<DrainContaminationCompanyInfo> resultInfo = new ResultInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            String type = request.getParameter("type");
            String pageNumber = request.getParameter("pageNumber");
            String pageSize = request.getParameter("pageSize");

            resultInfo.setData(drainContaminationCompanyInfoService.getDrainContaminationCompanyInfo(type, pageNumber, pageSize));
            resultInfo.setRecordCount(drainContaminationCompanyInfoService.getDrainContaminationCompanyInfoCount(type));
            resultInfo.setPageCount(Util.getPageCount(resultInfo.getRecordCount(), pageSize));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }

        return resultInfo;
    }

    /**
     * 获取顺义区环境重点排污单位类型列表
     *
     * @return
     */
    @ApiOperation(value = "获取顺义区环境重点排污单位类型列表", notes = "获取顺义区环境重点排污单位类型列表")
    @RequestMapping(value = "getDrainContaminationCompanyTypes", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<DrainContaminationCompanyTypes> getDrainContaminationCompanyTypes() {
        ResultInfo<DrainContaminationCompanyTypes> resultInfo = new ResultInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setData(drainContaminationCompanyInfoService.getDrainContaminationCompanyTypes());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }

        return resultInfo;
    }


    /**
     * 获取企业废水列表
     *
     * @return
     */
    @ApiOperation(value = "获取企业废水列表", notes = "获取企业废水列表")
    @RequestMapping(value = "getCountryMonitorWaterCompanyInfoList", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<CountryMonitorWaterCompanyInfoListDTO> getCountryMonitorWaterCompanyInfoList(ServletRequest request) {
        ResultObjInfo<CountryMonitorWaterCompanyInfoListDTO> resultObjInfo = new ResultObjInfo<>();
        try {

            CountryMonitorWaterCompanyInfoListDTO countryMonitorWaterCompanyInfoContainerDTO = new CountryMonitorWaterCompanyInfoListDTO();
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");
            String strQueryDate = request.getParameter("queryDate");
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (StringUtils.isBlank(strQueryDate)) {
                String strDate = Util.getStrDayFromDate(countryMonitorWaterCompanyInfoService.getNewestDate());
                date = dateFormat.parse(strDate);
            } else {
                date = dateFormat.parse(strQueryDate);
            }

            countryMonitorWaterCompanyInfoContainerDTO.getWwg().setAirIndicatorInfoList(cityAirService.getAirIndicatorByName("wwg"));
            countryMonitorWaterCompanyInfoContainerDTO.setCountryMonitorWaterCompanyInfoDTOS(countryMonitorWaterCompanyInfoService.getCountryMonitorWaterCompanyInfoArrayList(date));
            resultObjInfo.setData(countryMonitorWaterCompanyInfoContainerDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }


    /**
     * 获取企业废水信息
     *
     * @return
     */
    @ApiOperation(value = "获取企业废水信息", notes = "获取企业废水信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyName", value = "companyName", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "monitorName", value = "monitorName", required = false, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getCountryMonitorWaterCompanyInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<CountryMonitorWaterCompanyInfoContainerDTO> getCountryMonitorWaterCompanyInfos(ServletRequest request) {
        ResultObjInfo<CountryMonitorWaterCompanyInfoContainerDTO> resultObjInfo = new ResultObjInfo<>();
        try {

            CountryMonitorWaterCompanyInfoContainerDTO countryMonitorWaterCompanyInfoContainerDTO = new CountryMonitorWaterCompanyInfoContainerDTO();
            String companyName = request.getParameter("companyName");
            String monitorName = request.getParameter("monitorName");
            if (StringUtils.isBlank(companyName) || StringUtils.isBlank(monitorName)){
                resultObjInfo.setCode("500");
                resultObjInfo.setMsg("站点名称不能为空！");
            }else {
                resultObjInfo.setCode("200");
                resultObjInfo.setMsg("success");

                String strQueryDate = request.getParameter("queryDate");
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (StringUtils.isBlank(strQueryDate)) {
                    date = countryMonitorWaterCompanyInfoService.getNewestDate();
                } else {
                    date = dateFormat.parse(strQueryDate);
                }
                List<CountryMonitorWaterCompanyInfo> list= countryMonitorWaterCompanyInfoService.getCountryMonitorWaterCompanyInfo30d(companyName,monitorName,date);
                Collections.reverse(list);
                countryMonitorWaterCompanyInfoContainerDTO.setCountryMonitorWaterCompanyInfos(list);
                resultObjInfo.setData(countryMonitorWaterCompanyInfoContainerDTO);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    /**
     * 获取企业废气信息
     *
     * @return
     */
    @ApiOperation(value = "获取企业废气信息", notes = "获取企业废气信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "queryDate", value = "queryDate", required = false, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getCountryMonitorAirCompanyInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<CountryMonitorAirCompanyInfoContainerDTO> getCountryMonitorAirCompanyInfos(ServletRequest request) {
        ResultObjInfo<CountryMonitorAirCompanyInfoContainerDTO> resultObjInfo = new ResultObjInfo<>();
        try {
            CountryMonitorAirCompanyInfoContainerDTO countryMonitorAirCompanyInfoContainerDTO = new CountryMonitorAirCompanyInfoContainerDTO();
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");
            String pageNumber = request.getParameter("pageNumber");
            String pageSize = request.getParameter("pageSize");

            String strQueryDate = request.getParameter("queryDate");
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (StringUtils.isBlank(strQueryDate)) {
                String strDate = Util.getStrDayFromDate(countryMonitorAirCompanyInfoService.getNewestDate());
                date = dateFormat.parse(strDate);
            } else {
                date = dateFormat.parse(strQueryDate);
            }

            countryMonitorAirCompanyInfoContainerDTO.getAqi().setAirIndicatorInfoList(cityAirService.getAirIndicatorByName("aqi"));
            countryMonitorAirCompanyInfoContainerDTO.setCountryMonitorAirCompanyInfos(countryMonitorAirCompanyInfoService.getCountryMonitorAirCompanyInfo(pageNumber, pageSize, date));
            countryMonitorAirCompanyInfoContainerDTO.setRecordCount(countryMonitorAirCompanyInfoService.getCountryMonitorAirCompanyInfoCount(date));
            countryMonitorAirCompanyInfoContainerDTO.setPageCount(Util.getPageCount(countryMonitorAirCompanyInfoContainerDTO.getRecordCount(), pageSize));
            resultObjInfo.setData(countryMonitorAirCompanyInfoContainerDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    /**
     * 获取北京市下区县列表
     *
     * @return
     */
    @ApiOperation(value = "获取北京市下区县列表", notes = "获取北京市下区县列表")
    @RequestMapping(value = "getWeatherCityInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<WeatherCityInfo> getWeatherCityInfos() {
        ResultInfo<WeatherCityInfo> resultInfo = new ResultInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setData(weatherCityInfoService.getWeatherCityInfos());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }

        return resultInfo;
    }


    /**
     * 获取顺义区街道列表列表
     *
     * @return
     */
    @ApiOperation(value = "获取顺义区街道列表", notes = "获取顺义区街道列表")
    @RequestMapping(value = "getAvenueInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<AvenueInfo> getAvenueInfos() {
        ResultInfo<AvenueInfo> resultInfo = new ResultInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setData(avenueInfoService.getAvenueInfos());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }

        return resultInfo;
    }

    /**
     * 获取全国空气质量实时排名
     *
     * @return
     */
    @ApiOperation(value = "获取全国空气质量实时排名", notes = "获取全国空气质量实时排名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getAirRankingInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<AirRankingInfo> getAirRankingInfo(ServletRequest request, HttpSession session) {
        ResultInfo<AirRankingInfo> resultInfo = new ResultInfo<>();
        try {
            String pageSize = request.getParameter("pageSize");
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            PageRequest pageRequest = Util.buildPageRequest(request);
            resultInfo.setData(airRankingInfoService.getAirRankingInfo(pageRequest));
            long count = airRankingInfoService.getAirRankingInfoCount();
            resultInfo.setRecordCount(count);
            resultInfo.setPageCount(Util.getPageCount(count, pageSize));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }
        return resultInfo;
    }

    /**
     * 获取空气质量等级
     *
     * @return
     */
    @ApiOperation(value = "获取空气质量等级", notes = "获取空气质量等级")
    @RequestMapping(value = "getAirQualityGradeInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<AirIndicatorInfo> getAirQualityGradeInfo() {
        ResultInfo<AirIndicatorInfo> resultInfo = new ResultInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setData(cityAirService.getAirIndicatorByName("aqi"));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }
        return resultInfo;
    }

    /**
     * 生成天气信息
     */
    @ApiOperation(value = "生成天气信息(请勿随便调用)", notes = "不提供调用,只用来生成数据")
    @RequestMapping(value = "generateWeatherInfo", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String generateWeatherInfo() {
        try {
//            weatherInfoService.generateData();
            return "success";
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return "failure";
        }
    }

    /**
     * 生成北京下属区信息
     */
    @ApiOperation(value = "生成北京下属区信息(请勿随便调用)", notes = "不提供调用，只用来生成数据")
    @RequestMapping(value = "generateCityInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String generateCityInfos() {
        try {
            if (weatherCityInfoDao.count() <= 0) {
//                weatherCrawler.generateCityInfo();
            }
            return "success";
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return "failure";
        }
    }
}

