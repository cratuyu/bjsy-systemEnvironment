package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dao.CountryMonitorAirCompanyInfoDao;
import com.leadmap.environmentalrotection.entity.company.CountryMonitorAirCompanyInfo;
import com.leadmap.environmentalrotection.web.controller.AirController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
 * @Date: 2018/7/18 15:23
 */
@Component
public class CountryMonitorAirCompanyInfoService {
    private final static Logger logger = LoggerFactory.getLogger(AirController.class);

    @Autowired
    private CountryMonitorAirCompanyInfoDao countryMonitorAirCompanyInfoDao;


    public List<CountryMonitorAirCompanyInfo> getCountryMonitorAirCompanyInfo(String pageNumber, String pageSize, Date date) {
        if (countryMonitorAirCompanyInfoDao.count() == 0) {
            return null;
//            this.generateDataFromSite();
        }
        Specification<CountryMonitorAirCompanyInfo> spec = Util.getDaySpecification(date);
        PageRequest pageRequest = Util.buildPageRequest(pageNumber, pageSize, StringUtils.EMPTY);
        List<CountryMonitorAirCompanyInfo> countryMonitorWaterCompanyInfoArrayList = new ArrayList<>();
        Iterable<CountryMonitorAirCompanyInfo> gwtWaterInfoIterable = countryMonitorAirCompanyInfoDao.findAll(spec, pageRequest);
        for (CountryMonitorAirCompanyInfo countryMonitorAirCompanyInfo : gwtWaterInfoIterable) {
            if (countryMonitorAirCompanyInfo.getSo2DayMeanValue() == null) {
                countryMonitorAirCompanyInfo.setSo2DayMeanValue("-");
            }

            if (countryMonitorAirCompanyInfo.getNoDayMeanValue() == null) {
                countryMonitorAirCompanyInfo.setNoDayMeanValue("-");
            }

            if (countryMonitorAirCompanyInfo.getDayCumulativeFlow() == null) {
                countryMonitorAirCompanyInfo.setDayCumulativeFlow("-");
            }

            if (countryMonitorAirCompanyInfo.getGasFlow() == null) {
                countryMonitorAirCompanyInfo.setGasFlow("-");
            }

            if (countryMonitorAirCompanyInfo.getSo2Standard() == null) {
                countryMonitorAirCompanyInfo.setSo2Standard("-");
            }

            if (countryMonitorAirCompanyInfo.getNoStandard() == null) {
                countryMonitorAirCompanyInfo.setNoStandard("-");
            }

            generate24hInfo(countryMonitorAirCompanyInfo, date);
            generate30dInfo(countryMonitorAirCompanyInfo, date);
            countryMonitorWaterCompanyInfoArrayList.add(countryMonitorAirCompanyInfo);
        }

        return countryMonitorWaterCompanyInfoArrayList;
    }

    public long getCountryMonitorAirCompanyInfoCount(Date date) {
        Specification<CountryMonitorAirCompanyInfo> spec = Util.getDaySpecification(date);
        return countryMonitorAirCompanyInfoDao.count(spec);
    }

    public void generate24hInfo(CountryMonitorAirCompanyInfo countryMonitorAirCompanyInfo, Date date) {
        date = Util.getIntegralPointDay(date);
        Date current = date;
        Specification<CountryMonitorAirCompanyInfo> spec = new Specification<CountryMonitorAirCompanyInfo>() {
            @Override
            public Predicate toPredicate(Root<CountryMonitorAirCompanyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.equal(root.get("companyName"), countryMonitorAirCompanyInfo.getCompanyName()));
                predicates.add(criteriaBuilder.equal(root.get("monitorName"), countryMonitorAirCompanyInfo.getMonitorName()));

                Calendar c = Calendar.getInstance();
                c.setTime(current);
                c.add(Calendar.DAY_OF_MONTH, 1);
                Date nextDay = c.getTime();
                predicates.add(criteriaBuilder.lessThan(root.get("createTime"), nextDay));

                c = Calendar.getInstance();
                c.setTime(current);
                c.add(Calendar.DAY_OF_MONTH, -1);
                Date preDay = c.getTime();
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), preDay));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        Iterable<CountryMonitorAirCompanyInfo> gwtWaterInfoIterable = countryMonitorAirCompanyInfoDao.findAll(spec, sort);
        String so2DayMeanValue48h = "";
        String noDayMeanValue48h = "";
        String dayCumulativeFlow48h = "";
        String gasFlow48h = "";
        for (CountryMonitorAirCompanyInfo countryMonitorAirCompanyInfo1 : gwtWaterInfoIterable) {
            if (countryMonitorAirCompanyInfo1.getSo2DayMeanValue() == null) {
                countryMonitorAirCompanyInfo1.setSo2DayMeanValue("-");
            }

            if (countryMonitorAirCompanyInfo1.getNoDayMeanValue() == null) {
                countryMonitorAirCompanyInfo1.setNoDayMeanValue("-");
            }

            if (countryMonitorAirCompanyInfo1.getDayCumulativeFlow() == null) {
                countryMonitorAirCompanyInfo1.setDayCumulativeFlow("-");
            }

            if (countryMonitorAirCompanyInfo1.getGasFlow() == null) {
                countryMonitorAirCompanyInfo1.setGasFlow("-");
            }

            if (countryMonitorAirCompanyInfo.getSo2Standard() == null) {
                countryMonitorAirCompanyInfo.setSo2Standard("-");
            }

            if (countryMonitorAirCompanyInfo.getNoStandard() == null) {
                countryMonitorAirCompanyInfo.setNoStandard("-");
            }

            so2DayMeanValue48h += countryMonitorAirCompanyInfo1.getSo2DayMeanValue() + ",";
            noDayMeanValue48h += countryMonitorAirCompanyInfo1.getNoDayMeanValue() + ",";
            dayCumulativeFlow48h += countryMonitorAirCompanyInfo1.getDayCumulativeFlow() + ",";
            gasFlow48h += countryMonitorAirCompanyInfo1.getGasFlow() + ",";
        }
        countryMonitorAirCompanyInfo.setSo2DayMeanValue48h(so2DayMeanValue48h);
        countryMonitorAirCompanyInfo.setNoDayMeanValue48h(noDayMeanValue48h);
        countryMonitorAirCompanyInfo.setDayCumulativeFlow48h(dayCumulativeFlow48h);
        countryMonitorAirCompanyInfo.setGasFlow48h(gasFlow48h);
    }

    public void generate30dInfo(CountryMonitorAirCompanyInfo countryMonitorAirCompanyInfo, Date date) {
        date = Util.getIntegralPointDay(date);
        Date current = date;
        Specification<CountryMonitorAirCompanyInfo> spec = new Specification<CountryMonitorAirCompanyInfo>() {
            @Override
            public Predicate toPredicate(Root<CountryMonitorAirCompanyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.equal(root.get("companyName"), countryMonitorAirCompanyInfo.getCompanyName()));
                predicates.add(criteriaBuilder.equal(root.get("monitorName"), countryMonitorAirCompanyInfo.getMonitorName()));

                Calendar c = Calendar.getInstance();
                c.setTime(current);
                c.add(Calendar.DAY_OF_MONTH, 1);
                Date nextDay = c.getTime();
                predicates.add(criteriaBuilder.lessThan(root.get("createTime"), nextDay));

                c = Calendar.getInstance();
                c.setTime(current);
                c.add(Calendar.DAY_OF_MONTH, -29);
                Date preDay = c.getTime();
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), preDay));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        Iterable<CountryMonitorAirCompanyInfo> gwtWaterInfoIterable = countryMonitorAirCompanyInfoDao.findAll(spec, sort);
        String so2DayMeanValue30d = "";
        String noDayMeanValue30d = "";
        String dayCumulativeFlow30d = "";
        String gasFlow30d = "";
        for (CountryMonitorAirCompanyInfo countryMonitorAirCompanyInfo1 : gwtWaterInfoIterable) {
            if (countryMonitorAirCompanyInfo1.getSo2DayMeanValue() == null) {
                countryMonitorAirCompanyInfo1.setSo2DayMeanValue("-");
            }

            if (countryMonitorAirCompanyInfo1.getNoDayMeanValue() == null) {
                countryMonitorAirCompanyInfo1.setNoDayMeanValue("-");
            }

            if (countryMonitorAirCompanyInfo1.getDayCumulativeFlow() == null) {
                countryMonitorAirCompanyInfo1.setDayCumulativeFlow("-");
            }

            if (countryMonitorAirCompanyInfo1.getGasFlow() == null) {
                countryMonitorAirCompanyInfo1.setGasFlow("-");
            }

            if (countryMonitorAirCompanyInfo.getSo2Standard() == null) {
                countryMonitorAirCompanyInfo.setSo2Standard("-");
            }

            if (countryMonitorAirCompanyInfo.getNoStandard() == null) {
                countryMonitorAirCompanyInfo.setNoStandard("-");
            }

            so2DayMeanValue30d += countryMonitorAirCompanyInfo1.getSo2DayMeanValue() + ",";
            noDayMeanValue30d += countryMonitorAirCompanyInfo1.getNoDayMeanValue() + ",";
            dayCumulativeFlow30d += countryMonitorAirCompanyInfo1.getDayCumulativeFlow() + ",";
            gasFlow30d += countryMonitorAirCompanyInfo1.getGasFlow() + ",";
        }
        countryMonitorAirCompanyInfo.setSo2DayMeanValue30d(so2DayMeanValue30d);
        countryMonitorAirCompanyInfo.setNoDayMeanValue30d(noDayMeanValue30d);
        countryMonitorAirCompanyInfo.setDayCumulativeFlow30d(dayCumulativeFlow30d);
        countryMonitorAirCompanyInfo.setGasFlow30d(gasFlow30d);
    }

    @Cacheable(value = "countryMonitorAirCompanyInfogetNewestDate")
    public Date getNewestDate() {
        List<CountryMonitorAirCompanyInfo> companyInfos = countryMonitorAirCompanyInfoDao.findTop1ByOrderByCreateTimeDesc();
        if (companyInfos != null && companyInfos.size() > 0) {
            return companyInfos.get(0).getCreateTime();
        } else {
            return null;
        }
    }

}
