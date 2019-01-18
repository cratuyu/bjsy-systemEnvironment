package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dao.AirIndicatorInfoDao;
import com.leadmap.environmentalrotection.dao.AirQualityInfoDao;
import com.leadmap.environmentalrotection.dao.StationInfoDao;
import com.leadmap.environmentalrotection.dto.air.CityAirInfoDTO;
import com.leadmap.environmentalrotection.dto.air.DataContainerDTO;
import com.leadmap.environmentalrotection.dto.air.DataInfoDTO;
import com.leadmap.environmentalrotection.entity.air.AirIndicatorInfo;
import com.leadmap.environmentalrotection.entity.weather.AirQualityInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
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
public class CityAirService {

    private final static Logger logger = LoggerFactory.getLogger(CityAirService.class);

    @Value("${cityair.serviceurl}")
    private String serviceUrl;

    @Autowired
    private StationInfoDao stationInfoDao;

    @Autowired
    private AirIndicatorInfoDao airIndicatorInfoDao;

    @Autowired
    private AirQualityInfoDao airQualityInfoDao;


    @Cacheable(value = "getCityAirInfo")
    public CityAirInfoDTO getCityAirInfo(Date queryDate) {
        try {
            CityAirInfoDTO cityAirInfoDTO = new CityAirInfoDTO();
            List<AirQualityInfo> toDayAirQualityInfoArrayList = this.getAirQualityInfo(queryDate);
            List<AirQualityInfo> preDayAqi24hInfoArrayList = this.getAirQualityInfo(Util.getPreDate(queryDate));
            //AQI
            DataContainerDTO dataContainerDTO = new DataContainerDTO();
            for (AirQualityInfo aqi24hInfo : toDayAirQualityInfoArrayList) {
                DataInfoDTO dataInfoDTO = new DataInfoDTO();
                dataInfoDTO.setX(aqi24hInfo.getX());
                dataInfoDTO.setY(aqi24hInfo.getY());
                dataInfoDTO.setH24(aqi24hInfo.getAqi24h());
                dataInfoDTO.setStationName(aqi24hInfo.getStationName());
                //设置48小时aqi
                String h48 = "";
                if (preDayAqi24hInfoArrayList.size() > 0) {
                    for (AirQualityInfo preDayAqi24hInfo1 : preDayAqi24hInfoArrayList) {
                        if (preDayAqi24hInfo1.getStationName().equalsIgnoreCase(aqi24hInfo.getStationName())) {
                            h48 = preDayAqi24hInfo1.getAqi24h() + "," + aqi24hInfo.getAqi24h();
                            break;
                        }
                    }
                } else {
                    h48 = aqi24hInfo.getAqi24h();
                }

                dataInfoDTO.setH48(h48);
                //设置30天aqi
                String day30 = "";
                for (int i = 0; i < 30; i++) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(queryDate);
                    c.add(Calendar.DAY_OF_MONTH, -i);
                    Date preDay = c.getTime();
                    List<AirQualityInfo> airQualityInfoArrayList1 = this.getAirQualityInfo(preDay);
                    for (AirQualityInfo airQualityInfo : airQualityInfoArrayList1) {
                        if (airQualityInfo.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                            day30 = ((Float) (Float.parseFloat(airQualityInfo.getAqi()))).toString() + "," + day30;
                            break;
                        }
                    }
                }
                dataInfoDTO.setDay30(day30);
                for (AirQualityInfo airQualityInfo : toDayAirQualityInfoArrayList) {
                    if (airQualityInfo.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                        dataInfoDTO.setAvg(((Float) (Float.parseFloat(airQualityInfo.getAqi()))).toString());
                        break;
                    }
                }
                dataContainerDTO.getDataInfoDTOList().add(dataInfoDTO);
                dataContainerDTO.setAirIndicatorInfoList(getAirIndicatorByName("aqi"));
            }
            cityAirInfoDTO.setAqi(dataContainerDTO);
            List<List<AirQualityInfo>> airQualityInfoListList = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                Calendar c = Calendar.getInstance();
                c.setTime(queryDate);
                c.add(Calendar.DAY_OF_MONTH, -i);
                Date preDay = c.getTime();
                List<AirQualityInfo> airQualityInfoArrayList1 = this.getAirQualityInfo(preDay);
                if (airQualityInfoArrayList1.size() > 0) {
                    airQualityInfoListList.add(airQualityInfoArrayList1);
                }
            }
            //pm25
            dataContainerDTO = new DataContainerDTO();
            for (AirQualityInfo airQualityInfo : toDayAirQualityInfoArrayList) {
                DataInfoDTO dataInfoDTO = new DataInfoDTO();
                dataInfoDTO.setStationName(airQualityInfo.getStationName());
                String avg = airQualityInfo.getPm25avg();
                if (StringUtils.isBlank(avg)) {
                    avg = "0.0";
                }
                dataInfoDTO.setAvg(((Float) (Float.parseFloat(avg))).toString());
                dataInfoDTO.setH24(airQualityInfo.getPm2524h());
                dataInfoDTO.setX(airQualityInfo.getX());
                dataInfoDTO.setY(airQualityInfo.getY());
                dataContainerDTO.getDataInfoDTOList().add(dataInfoDTO);
                dataContainerDTO.setAirIndicatorInfoList(getAirIndicatorByName("pm25"));

                String h48 = "";
                String day30 = "";
                if (airQualityInfoListList.size() > 0) {
                    if (airQualityInfoListList.size() > 1) {
                        List<AirQualityInfo> preDayAirQualityInfoArrayList = airQualityInfoListList.get(1);
                        for (AirQualityInfo airQualityInfo1 : preDayAirQualityInfoArrayList) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                h48 = airQualityInfo1.getPm2524h() + "," + airQualityInfo.getPm2524h();
                                break;
                            }
                        }
                    } else {
                        h48 = airQualityInfo.getPm2524h();
                    }

                    for (List<AirQualityInfo> preDayAirQualityInfoArrayList1 : airQualityInfoListList) {
                        for (AirQualityInfo airQualityInfo1 : preDayAirQualityInfoArrayList1) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                String avg1 = airQualityInfo.getPm25avg();
                                if (StringUtils.isBlank(avg1)) {
                                    avg1 = "0.0";
                                }
                                day30 = ((Float) (Float.parseFloat(avg1))).toString() + "," + day30;
                                break;
                            }
                        }
                    }
                }
                dataInfoDTO.setH48(h48);
                dataInfoDTO.setDay30(day30);
            }
            cityAirInfoDTO.setPm25(dataContainerDTO);
            //so2
            dataContainerDTO = new DataContainerDTO();
            for (AirQualityInfo airQualityInfo : toDayAirQualityInfoArrayList) {
                DataInfoDTO dataInfoDTO = new DataInfoDTO();
                dataInfoDTO.setStationName(airQualityInfo.getStationName());
                String avg = airQualityInfo.getSo2();
                if (StringUtils.isBlank(avg)) {
                    avg = "0.0";
                }
                dataInfoDTO.setAvg(((Float) (Float.parseFloat(avg))).toString());
                dataInfoDTO.setH24(airQualityInfo.getSo224h());
                dataInfoDTO.setX(airQualityInfo.getX());
                dataInfoDTO.setY(airQualityInfo.getY());
                dataContainerDTO.getDataInfoDTOList().add(dataInfoDTO);
                dataContainerDTO.setAirIndicatorInfoList(getAirIndicatorByName("so2"));

                String h48 = "";
                String day30 = "";
                if (airQualityInfoListList.size() > 0) {
                    if (airQualityInfoListList.size() > 1) {
                        List<AirQualityInfo> preDayAirQualityInfoArrayList = airQualityInfoListList.get(1);
                        for (AirQualityInfo airQualityInfo1 : preDayAirQualityInfoArrayList) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                h48 = airQualityInfo1.getSo224h() + "," + airQualityInfo.getSo224h();
                                break;
                            }
                        }
                    } else {
                        h48 = airQualityInfo.getSo224h();
                    }

                    for (List<AirQualityInfo> preDayAirQualityInfoArrayList1 : airQualityInfoListList) {
                        for (AirQualityInfo airQualityInfo1 : preDayAirQualityInfoArrayList1) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                String avg1 = airQualityInfo.getSo2avg();
                                if (StringUtils.isBlank(avg1)) {
                                    avg1 = "0.0";
                                }
                                day30 = ((Float) (Float.parseFloat(avg1))).toString() + "," + day30;
                                break;
                            }
                        }
                    }
                }
                dataInfoDTO.setH48(h48);
                dataInfoDTO.setDay30(day30);
            }
            cityAirInfoDTO.setSo2(dataContainerDTO);
            //no2
            dataContainerDTO = new DataContainerDTO();
            for (AirQualityInfo airQualityInfo : toDayAirQualityInfoArrayList) {
                DataInfoDTO dataInfoDTO = new DataInfoDTO();
                dataInfoDTO.setStationName(airQualityInfo.getStationName());
                String avg = airQualityInfo.getNo2();
                if (StringUtils.isBlank(avg)) {
                    avg = "0.0";
                }
                dataInfoDTO.setAvg(((Float) (Float.parseFloat(avg))).toString());
                dataInfoDTO.setH24(airQualityInfo.getNo224h());
                dataInfoDTO.setX(airQualityInfo.getX());
                dataInfoDTO.setY(airQualityInfo.getY());
                dataContainerDTO.getDataInfoDTOList().add(dataInfoDTO);
                dataContainerDTO.setAirIndicatorInfoList(getAirIndicatorByName("no2"));

                String h48 = "";
                String day30 = "";
                if (airQualityInfoListList.size() > 0) {
                    if (airQualityInfoListList.size() > 1) {
                        List<AirQualityInfo> preDayAirQualityInfoArrayList = airQualityInfoListList.get(1);
                        for (AirQualityInfo airQualityInfo1 : preDayAirQualityInfoArrayList) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                h48 = airQualityInfo1.getNo224h() + "," + airQualityInfo.getNo224h();
                                break;
                            }
                        }
                    } else {
                        h48 = airQualityInfo.getNo224h();
                    }

                    for (List<AirQualityInfo> preDayAirQualityInfoArrayList1 : airQualityInfoListList) {
                        for (AirQualityInfo airQualityInfo1 : preDayAirQualityInfoArrayList1) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                String avg1 = airQualityInfo.getNo2avg();
                                if (StringUtils.isBlank(avg1)) {
                                    avg1 = "0.0";
                                }
                                day30 = ((Float) (Float.parseFloat(avg1))).toString() + "," + day30;
                                break;
                            }
                        }
                    }
                }
                dataInfoDTO.setH48(h48);
                dataInfoDTO.setDay30(day30);
            }
            cityAirInfoDTO.setNo2(dataContainerDTO);
            //o3
            dataContainerDTO = new DataContainerDTO();
            for (AirQualityInfo airQualityInfo : toDayAirQualityInfoArrayList) {
                DataInfoDTO dataInfoDTO = new DataInfoDTO();
                dataInfoDTO.setStationName(airQualityInfo.getStationName());
                String avg = airQualityInfo.getO3();
                if (StringUtils.isBlank(avg)) {
                    avg = "0.0";
                }
                dataInfoDTO.setAvg(((Float) (Float.parseFloat(avg))).toString());
                dataInfoDTO.setH24(airQualityInfo.getO324h());
                dataInfoDTO.setX(airQualityInfo.getX());
                dataInfoDTO.setY(airQualityInfo.getY());
                dataContainerDTO.getDataInfoDTOList().add(dataInfoDTO);
                dataContainerDTO.setAirIndicatorInfoList(getAirIndicatorByName("o3"));

                String h48 = "";
                String day30 = "";
                if (airQualityInfoListList.size() > 0) {
                    if (airQualityInfoListList.size() > 1) {
                        List<AirQualityInfo> preDayAirQualityInfoArrayList = airQualityInfoListList.get(1);
                        for (AirQualityInfo airQualityInfo1 : preDayAirQualityInfoArrayList) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                h48 = airQualityInfo1.getO324h() + "," + airQualityInfo.getO324h();
                                break;
                            }
                        }
                    } else {
                        h48 = airQualityInfo.getO324h();
                    }

                    for (List<AirQualityInfo> preDayAirQualityInfoArrayList1 : airQualityInfoListList) {
                        for (AirQualityInfo airQualityInfo1 : preDayAirQualityInfoArrayList1) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                String avg1 = airQualityInfo.getO3avg();
                                if (StringUtils.isBlank(avg1)) {
                                    avg1 = "0.0";
                                }
                                day30 = ((Float) (Float.parseFloat(avg1))).toString() + "," + day30;
                                break;
                            }
                        }
                    }
                }
                dataInfoDTO.setH48(h48);
                dataInfoDTO.setDay30(day30);
            }
            cityAirInfoDTO.setO3(dataContainerDTO);
            //co
            dataContainerDTO = new DataContainerDTO();
            for (AirQualityInfo airQualityInfo : toDayAirQualityInfoArrayList) {
                DataInfoDTO dataInfoDTO = new DataInfoDTO();
                dataInfoDTO.setStationName(airQualityInfo.getStationName());
                String avg = airQualityInfo.getCo();
                if (StringUtils.isBlank(avg)) {
                    avg = "0.0";
                }
                dataInfoDTO.setAvg(((Float) (Float.parseFloat(avg))).toString());
                dataInfoDTO.setH24(airQualityInfo.getCo24h());
                dataInfoDTO.setX(airQualityInfo.getX());
                dataInfoDTO.setY(airQualityInfo.getY());
                dataContainerDTO.getDataInfoDTOList().add(dataInfoDTO);
                dataContainerDTO.setAirIndicatorInfoList(getAirIndicatorByName("co"));

                String h48 = "";
                String day30 = "";
                if (airQualityInfoListList.size() > 0) {
                    if (airQualityInfoListList.size() > 1) {
                        List<AirQualityInfo> preDayAirQualityInfoArrayList = airQualityInfoListList.get(1);
                        for (AirQualityInfo airQualityInfo1 : preDayAirQualityInfoArrayList) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                h48 = airQualityInfo1.getCo24h() + "," + airQualityInfo.getCo24h();
                                break;
                            }
                        }
                    } else {
                        h48 = airQualityInfo.getCo24h();
                    }

                    for (List<AirQualityInfo> preDayAirQualityInfoArrayList1 : airQualityInfoListList) {
                        for (AirQualityInfo airQualityInfo1 : preDayAirQualityInfoArrayList1) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                String avg1 = airQualityInfo.getCoavg();
                                if (StringUtils.isBlank(avg1)) {
                                    avg1 = "0.0";
                                }
                                day30 = ((Float) (Float.parseFloat(avg1))).toString() + "," + day30;
                                break;
                            }
                        }
                    }
                }
                dataInfoDTO.setH48(h48);
                dataInfoDTO.setDay30(day30);
            }
            cityAirInfoDTO.setCo(dataContainerDTO);
            //pm10
            dataContainerDTO = new DataContainerDTO();
            for (AirQualityInfo airQualityInfo : toDayAirQualityInfoArrayList) {
                DataInfoDTO dataInfoDTO = new DataInfoDTO();
                dataInfoDTO.setStationName(airQualityInfo.getStationName());
                String avg = airQualityInfo.getPm10();
                if (StringUtils.isBlank(avg)) {
                    avg = "0.0";
                }
                dataInfoDTO.setAvg(((Float) (Float.parseFloat(avg))).toString());
                dataInfoDTO.setH24(airQualityInfo.getPm1024h());
                dataInfoDTO.setX(airQualityInfo.getX());
                dataInfoDTO.setY(airQualityInfo.getY());
                dataContainerDTO.getDataInfoDTOList().add(dataInfoDTO);
                dataContainerDTO.setAirIndicatorInfoList(getAirIndicatorByName("pm10"));

                String h48 = "";
                String day30 = "";
                if (airQualityInfoListList.size() > 0) {
                    if (airQualityInfoListList.size() > 1) {
                        List<AirQualityInfo> preDayAirQualityInfoArrayList = airQualityInfoListList.get(1);
                        for (AirQualityInfo airQualityInfo1 : preDayAirQualityInfoArrayList) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                h48 = airQualityInfo1.getPm1024h() + "," + airQualityInfo.getPm1024h();
                                break;
                            }
                        }
                    } else {
                        h48 = airQualityInfo.getPm1024h();
                    }

                    for (List<AirQualityInfo> day30AirQualityInfoArrayList1 : airQualityInfoListList) {
                        for (AirQualityInfo airQualityInfo1 : day30AirQualityInfoArrayList1) {
                            if (airQualityInfo1.getStationName().equalsIgnoreCase(dataInfoDTO.getStationName())) {
                                String avg1 = airQualityInfo.getPm10avg();
                                if (StringUtils.isBlank(avg1)) {
                                    avg1 = "0.0";
                                }
                                day30 = ((Float) (Float.parseFloat(avg1))).toString() + "," + day30;
                                break;
                            }
                        }
                    }
                }
                dataInfoDTO.setH48(h48);
                dataInfoDTO.setDay30(day30);
            }
            cityAirInfoDTO.setPm10(dataContainerDTO);
            return cityAirInfoDTO;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    @Cacheable(value = "getAirIndicatorByName")
    public List<AirIndicatorInfo> getAirIndicatorByName(String name) {
        return airIndicatorInfoDao.findByName(name);
    }

    public List<AirQualityInfo> getAirQualityInfo(Date queryDate) {
        try {
            return airQualityInfoDao.findAll(Util.getDaySpecification(queryDate));
//            List<AirQualityInfo> aqi24hInfoArrayList = new ArrayList<>();
//            if (airQualityInfoDao.count() > 0) {
//                Iterable<AirQualityInfo> airQualityInfoDaoAll = airQualityInfoDao.findAll();
//                for (AirQualityInfo airQualityInfo : airQualityInfoDaoAll) {
////                    long cha = (new Date()).getTime() - airQualityInfo.getCreateTime().getTime();
////                    double result = cha * 1.0 / (1000 * 60 * 60);
//                    if (Util.checkDateIsMoreThanSpecialMilliseconds(new Date(), airQualityInfo.getCreateTime(), 1000 * 60 * 60)) {
//                        return generateData();
//                    } else {
//                        aqi24hInfoArrayList.add(airQualityInfo);
//                    }
//                }
//                return aqi24hInfoArrayList;
//            } else {
//                return generateData();
//            }
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }


    @Cacheable(value = "getAirQualityInfoNewestDate")
    public Date getNewestDate() {
        List<AirQualityInfo> gwtWaterInfoList = airQualityInfoDao.findTop1ByOrderByCreateTimeDesc();
        if (gwtWaterInfoList != null && gwtWaterInfoList.size() > 0) {
            return gwtWaterInfoList.get(0).getCreateTime();
        } else {
            return null;
        }
    }
}
