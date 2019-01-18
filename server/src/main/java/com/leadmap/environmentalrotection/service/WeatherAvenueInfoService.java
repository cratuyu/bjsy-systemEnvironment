package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.common.httprequest.HttpRequest;
import com.leadmap.environmentalrotection.dao.AvenueInfoDao;
import com.leadmap.environmentalrotection.dao.AvenueWeatherInfoDao;
import com.leadmap.environmentalrotection.dao.AvenueWeatherInfoInDayDao;
import com.leadmap.environmentalrotection.entity.weather.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/17 18:46
 */
@Component
public class WeatherAvenueInfoService {

    private final static Logger logger = LoggerFactory.getLogger(WeatherAvenueInfoService.class);

    @Value("${shunYiAvenueUrl}")
    private String shunYiAvenueUrl;

    @Autowired
    private AvenueInfoDao avenueInfoDao;

    @Autowired
    private AvenueWeatherInfoDao avenueWeatherInfoDao;

    @Autowired
    private AvenueWeatherInfoInDayDao avenueWeatherInfoInDayDao;


    @Cacheable(value = "getAvenueWeatherInfoNewestDate")
    public Date getNewestDate() {
        List<AvenueWeatherInfo> avenueWeatherInfoList = avenueWeatherInfoDao.findTop1ByOrderByCreateTimeDesc();
        if (avenueWeatherInfoList != null && avenueWeatherInfoList.size() > 0) {
            return avenueWeatherInfoList.get(0).getCreateTime();
        } else {
            return null;
        }
    }

    /**
     * 根据坐标获取街道地址   返回街道id
     *
     * @param gdApiUrl
     * @param parameter
     * @return
     */
    public String getCoordinateAddress(String gdApiUrl, String parameter) {
        try {
            String str = HttpRequest.sendGet(gdApiUrl, parameter);
            String avenueId;

            JSONObject jsonObj = JSONObject.fromObject(str);
            JSONObject regeocode = (JSONObject) jsonObj.get("regeocode");

            JSONObject jsonObj1 = JSONObject.fromObject(regeocode);
            JSONObject addressComponent = (JSONObject) jsonObj1.get("addressComponent");

            JSONObject jsonObj2 = JSONObject.fromObject(addressComponent);
            String township;
            try {
                township = (String) jsonObj2.get("township");
            } catch (Exception ex) {
                township = "顺义区";
            }
            AvenueInfo avenueInfo = avenueInfoDao.findByMonitoringSite(township);
            if (avenueInfo != null) {
                avenueId = avenueInfo.getAvenueId();
            } else {
                avenueId = "101010400";
            }
            return avenueId;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }


    /**
     * 获取街道天气
     *
     * @param avenueId
     * @param weatherImagesRootUrl
     * @param queryDate
     * @return
     */
    @Cacheable(value = "getAvenueWeatherInfo")
    public AvenueWeatherInfo getAvenueWeatherInfo(String avenueId, String weatherImagesRootUrl, Date queryDate) {
        Specification<AvenueWeatherInfo> spec = new Specification<AvenueWeatherInfo>() {

            @Override
            public Predicate toPredicate(Root<AvenueWeatherInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.equal(root.get("avenueId"), avenueId));
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), queryDate));

                Calendar c = Calendar.getInstance();
                c.setTime(queryDate);
                c.add(Calendar.DAY_OF_MONTH, 1);
                Date nextDay = c.getTime();
                predicates.add(criteriaBuilder.lessThan(root.get("createTime"), nextDay));

//                predicates.add(criteriaBuilder.equal(root.get("avenueId"), avenueId));
//                predicates.add(criteriaBuilder.equal(root.get("time"), queryDate));

//                Calendar c = Calendar.getInstance();
//                c.setTime(queryDate);
//                c.add(Calendar.DAY_OF_MONTH, 1);
//                Date nextDay = c.getTime();
//                predicates.add(criteriaBuilder.lessThan(root.get("time"), nextDay));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        List<AvenueWeatherInfo> weatherInfoList = avenueWeatherInfoDao.findAll(spec);
        if (weatherInfoList != null && weatherInfoList.size() > 0) {
            for (AvenueWeatherInfo avenueWeatherInfo : weatherInfoList) {
                Specification<AvenueWeatherInfoInDay> spec1 = new Specification<AvenueWeatherInfoInDay>() {
                    @Override
                    public Predicate toPredicate(Root<AvenueWeatherInfoInDay> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicates = new ArrayList<>();

                        predicates.add(criteriaBuilder.equal(root.get("avenueId"), avenueId));
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), queryDate));

                        Calendar c = Calendar.getInstance();
                        c.setTime(queryDate);
                        c.add(Calendar.DAY_OF_MONTH, 1);
                        Date nextDay = c.getTime();
                        predicates.add(criteriaBuilder.lessThan(root.get("createTime"), nextDay));

                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                };

                avenueWeatherInfo.setAvenueWeatherInfo15DayList(avenueWeatherInfoInDayDao.findAll(spec1));
                if (avenueWeatherInfo.getAvenueWeatherInfo15DayList().size() > 0) {
                    avenueWeatherInfo.setWeatherStatus(avenueWeatherInfo.getAvenueWeatherInfo15DayList().get(0).getWeatherStatus());
                }

                java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                avenueWeatherInfo.setStrTime(format1.format(avenueWeatherInfo.getTime()));

                if (avenueWeatherInfo.getAvenueWeatherInfoInHourList() != null && avenueWeatherInfo.getAvenueWeatherInfoInHourList().size() > 0) {
                    for (AvenueWeatherInfoInHour avenueWeatherInfoInHour : avenueWeatherInfo.getAvenueWeatherInfoInHourList()) {
                        String tempUrl = avenueWeatherInfoInHour.getWeatherImgUrl();
                        if (tempUrl != null && tempUrl != "") {
                            avenueWeatherInfoInHour.setWeatherStatusImgUrl(weatherImagesRootUrl + tempUrl);
                        }
                    }
                }

                LifeIndex lifeIndex = new LifeIndex();

                lifeIndex.setUltravioletRays(avenueWeatherInfo.getUltravioletRays());
                lifeIndex.setUltravioletRaysLevel(avenueWeatherInfo.getUltravioletRaysLevel());

                lifeIndex.setCold(avenueWeatherInfo.getCold());
                lifeIndex.setColdLevel(avenueWeatherInfo.getColdLevel());

                lifeIndex.setDress(avenueWeatherInfo.getDress());
                lifeIndex.setDressLevel(avenueWeatherInfo.getDressLevel());

                lifeIndex.setCarWash(avenueWeatherInfo.getCarWash());
                lifeIndex.setCarWashLevel(avenueWeatherInfo.getCarWashLevel());

                lifeIndex.setMotion(avenueWeatherInfo.getMotion());
                lifeIndex.setMotionLevel(avenueWeatherInfo.getMotionLevel());

                lifeIndex.setAirPollutionDispersion(avenueWeatherInfo.getAirPollutionDispersion());
                lifeIndex.setAirPollutionDispersionLevel(avenueWeatherInfo.getAirPollutionDispersionLevel());

                avenueWeatherInfo.setLifeIndex(lifeIndex);

            }
        }
        if (weatherInfoList != null && weatherInfoList.size() > 0) {
            return weatherInfoList.get(0);
        } else {
            return null;
        }
    }

}
