package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.dao.WeatherCityInfoDao;
import com.leadmap.environmentalrotection.dao.WeatherInfoDao;
import com.leadmap.environmentalrotection.dao.WeatherInfoInDayDao;
import com.leadmap.environmentalrotection.entity.weather.LifeIndex;
import com.leadmap.environmentalrotection.entity.weather.WeatherInfo;
import com.leadmap.environmentalrotection.entity.weather.WeatherInfoInDay;
import com.leadmap.environmentalrotection.entity.weather.WeatherInfoInHour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author: ttq
 * @Date: 2018/7/18 16:41
 */
@Component
public class WeatherInfoService {

    private final static Logger logger = LoggerFactory.getLogger(WeatherInfoService.class);

    @Autowired
    private WeatherInfoDao weatherInfoDao;

    @Autowired
    private WeatherInfoInDayDao weatherInfoInDayDao;

//    @Autowired
//    private WeatherCrawler weatherCrawler;

    @Autowired
    private WeatherCityInfoDao weatherCityInfoDao;

    @Cacheable(value = "getWeatherInfo")
    public WeatherInfo getWeatherInfo(String cityId, String weatherImagesRootUrl, Date queryDate) {
        Specification<WeatherInfo> spec = new Specification<WeatherInfo>() {
            @Override
            public Predicate toPredicate(Root<WeatherInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.equal(root.get("cityId"), cityId));
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), queryDate));

                Calendar c = Calendar.getInstance();
                c.setTime(queryDate);
                c.add(Calendar.DAY_OF_MONTH, 1);
                Date nextDay = c.getTime();
                predicates.add(criteriaBuilder.lessThan(root.get("createTime"), nextDay));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        List<WeatherInfo> weatherInfoList = weatherInfoDao.findAll(spec);
//        if (weatherInfoList.size() == 0) {
//            this.generateData();
//        }
        if (weatherInfoList != null && weatherInfoList.size() > 0) {
            for (WeatherInfo weatherInfo : weatherInfoList) {
                Specification<WeatherInfoInDay> spec1 = new Specification<WeatherInfoInDay>() {
                    @Override
                    public Predicate toPredicate(Root<WeatherInfoInDay> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicates = new ArrayList<>();

                        predicates.add(criteriaBuilder.equal(root.get("cityId"), cityId));
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), queryDate));

                        Calendar c = Calendar.getInstance();
                        c.setTime(queryDate);
                        c.add(Calendar.DAY_OF_MONTH, 1);
                        Date nextDay = c.getTime();
                        predicates.add(criteriaBuilder.lessThan(root.get("createTime"), nextDay));

                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                };

                weatherInfo.setWeatherInfo15DayList(weatherInfoInDayDao.findAll(spec1));
                if (weatherInfo.getWeatherInfo15DayList().size() > 0) {
                    weatherInfo.setWeatherStatus(weatherInfo.getWeatherInfo15DayList().get(0).getWeatherStatus());
                }

                java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                weatherInfo.setStrTime(format1.format(weatherInfo.getTime()));

                if (weatherInfo.getWeatherInfoInHourList() != null && weatherInfo.getWeatherInfoInHourList().size() > 0) {
                    for (WeatherInfoInHour weatherInfoInHour : weatherInfo.getWeatherInfoInHourList()) {
                        String tempUrl = weatherInfoInHour.getWeatherImgUrl();
                        if (tempUrl != null && tempUrl != "") {
                            weatherInfoInHour.setWeatherStatusImgUrl(weatherImagesRootUrl + tempUrl);
                        }
                    }
                }

                LifeIndex lifeIndex = new LifeIndex();

                lifeIndex.setAirPollutionDispersion(weatherInfo.getAirPollutionDispersion());
                lifeIndex.setAirPollutionDispersionLevel(weatherInfo.getAirPollutionDispersionLevel());

                lifeIndex.setUltravioletRays(weatherInfo.getUltravioletRays());
                lifeIndex.setUltravioletRaysLevel(weatherInfo.getUltravioletRaysLevel());

//                lifeIndex.setReduceWeight(weatherInfo.getReduceWeight());
//                lifeIndex.setReduceWeightLevel(weatherInfo.getReduceWeightLevel());

                lifeIndex.setMotion(weatherInfo.getReduceWeight());
                lifeIndex.setMotionLevel(weatherInfo.getReduceWeightLevel());

//                lifeIndex.setBloodSugar(weatherInfo.getBloodSugar());
//                lifeIndex.setBloodSugarLevel(weatherInfo.getBloodSugarLevel());

                lifeIndex.setCold(weatherInfo.getBloodSugar());
                lifeIndex.setColdLevel(weatherInfo.getBloodSugarLevel());

                lifeIndex.setDress(weatherInfo.getDress());
                lifeIndex.setDressLevel(weatherInfo.getDressLevel());

                lifeIndex.setCarWash(weatherInfo.getCarWash());
                lifeIndex.setCarWashLevel(weatherInfo.getCarWashLevel());

                weatherInfo.setLifeIndex(lifeIndex);

            }
        }
        if (weatherInfoList != null && weatherInfoList.size() > 0) {
            return weatherInfoList.get(0);
        } else {
            return null;
        }
    }


    @Cacheable(value = "getWeatherInfoNewestDate")
    public Date getNewestDate() {
        List<WeatherInfo> gwtWaterInfoList = weatherInfoDao.findTop1ByOrderByCreateTimeDesc();
        if (gwtWaterInfoList != null && gwtWaterInfoList.size() > 0) {
            return gwtWaterInfoList.get(0).getCreateTime();
        } else {
            return null;
        }
    }
}
