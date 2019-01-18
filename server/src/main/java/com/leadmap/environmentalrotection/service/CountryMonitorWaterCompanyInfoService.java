package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dao.CountryMonitorWaterCompanyInfoDao;
import com.leadmap.environmentalrotection.dto.CountryMonitorWaterCompanyInfoDTO;
import com.leadmap.environmentalrotection.entity.company.CountryMonitorWaterCompanyInfo;
import com.leadmap.environmentalrotection.web.controller.AirController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
 * @Date: 2018/7/18 15:17
 */
@Component
public class CountryMonitorWaterCompanyInfoService {
    private final static Logger logger = LoggerFactory.getLogger(AirController.class);

    @Autowired
    private CountryMonitorWaterCompanyInfoDao countryMonitorWaterCompanyInfoDao;


    @Cacheable(value = "getCountryMonitorWaterCompanyInfoArrayList")
    public List<CountryMonitorWaterCompanyInfo> getCountryMonitorWaterCompanyInfoArrayList(String companyName,String monitorName, Date date) {
        if (countryMonitorWaterCompanyInfoDao.count() == 0) {
            return null;
        }
        Specification<CountryMonitorWaterCompanyInfo> spec = Util.getDaySpecification(date);

        List<CountryMonitorWaterCompanyInfo> countryMonitorWaterCompanyInfoArrayList = new ArrayList<>();
        Iterable<CountryMonitorWaterCompanyInfo> companyInfos = countryMonitorWaterCompanyInfoDao.getCountryMonitorWaterInfoList(date,companyName ,monitorName);
//        try {
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String strDate = Util.getStrDayFromDate(date);
//            date = dateFormat.parse(strDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        for (CountryMonitorWaterCompanyInfo countryMonitorWaterCompanyInfo : companyInfos) {
//            generate24hInfo(countryMonitorWaterCompanyInfo, date);
//            generate30dInfo(countryMonitorWaterCompanyInfo, date);
            countryMonitorWaterCompanyInfoArrayList.add(countryMonitorWaterCompanyInfo);
        }
        return countryMonitorWaterCompanyInfoArrayList;
    }

    public long getCountryMonitorWaterCompanyInfoCount(Date date) {
        Specification<CountryMonitorWaterCompanyInfo> spec = Util.getDaySpecification(date);
        return countryMonitorWaterCompanyInfoDao.count(spec);
    }

    public void generate24hInfo(CountryMonitorWaterCompanyInfo countryMonitorAirCompanyInfo, Date date) {
        date = Util.getIntegralPointDay(date);
        Date current = date;
        Specification<CountryMonitorWaterCompanyInfo> spec = new Specification<CountryMonitorWaterCompanyInfo>() {
            @Override
            public Predicate toPredicate(Root<CountryMonitorWaterCompanyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
        Iterable<CountryMonitorWaterCompanyInfo> gwtWaterInfoIterable = countryMonitorWaterCompanyInfoDao.findAll(spec, sort);
        String codMeanValue48h = "";
        String nh4DayMeanValue48h = "";
        String dayCumulativeFlow48h = "";
        for (CountryMonitorWaterCompanyInfo countryMonitorAirCompanyInfo1 : gwtWaterInfoIterable) {
            codMeanValue48h += countryMonitorAirCompanyInfo1.getCodDayMeanValue() + ",";
            nh4DayMeanValue48h += countryMonitorAirCompanyInfo1.getNh4DayMeanValue() + ",";
            dayCumulativeFlow48h += countryMonitorAirCompanyInfo1.getDayCumulativeFlow() + ",";
        }
//        countryMonitorAirCompanyInfo.setCodDayMeanValue48h(codMeanValue48h);
//        countryMonitorAirCompanyInfo.setNh4DayMeanValue48h(nh4DayMeanValue48h);
//        countryMonitorAirCompanyInfo.setDayCumulativeFlow48h(dayCumulativeFlow48h);
    }

    public void generate30dInfo(CountryMonitorWaterCompanyInfo countryMonitorAirCompanyInfo, Date date) {
        date = Util.getIntegralPointDay(date);
        Date current = date;
        Specification<CountryMonitorWaterCompanyInfo> spec = new Specification<CountryMonitorWaterCompanyInfo>() {
            @Override
            public Predicate toPredicate(Root<CountryMonitorWaterCompanyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
        Iterable<CountryMonitorWaterCompanyInfo> gwtWaterInfoIterable = countryMonitorWaterCompanyInfoDao.findAll(spec, sort);
        String codDayMeanValue30d = "";
        String nh4DayMeanValue30d = "";
        String dayCumulativeFlow30d = "";
        for (CountryMonitorWaterCompanyInfo countryMonitorAirCompanyInfo1 : gwtWaterInfoIterable) {
            codDayMeanValue30d += countryMonitorAirCompanyInfo1.getCodDayMeanValue() + ",";
            nh4DayMeanValue30d += countryMonitorAirCompanyInfo1.getNh4DayMeanValue() + ",";
            dayCumulativeFlow30d += countryMonitorAirCompanyInfo1.getDayCumulativeFlow() + ",";
        }
//        countryMonitorAirCompanyInfo.setCodDayMeanValue30d(codDayMeanValue30d);
//        countryMonitorAirCompanyInfo.setNh4DayMeanValue30d(nh4DayMeanValue30d);
//        countryMonitorAirCompanyInfo.setDayCumulativeFlow30d(dayCumulativeFlow30d);
    }

    public Date getNewestDate() {
        List<CountryMonitorWaterCompanyInfo> gwtWaterInfoList = countryMonitorWaterCompanyInfoDao.findTop1ByOrderByCreateTimeDesc();
        if (gwtWaterInfoList != null && gwtWaterInfoList.size() > 0) {
            return gwtWaterInfoList.get(0).getCreateTime();
        } else {
            return null;
        }
    }

    public List<CountryMonitorWaterCompanyInfoDTO> getCountryMonitorWaterCompanyInfoArrayList(Date date) {
        if (countryMonitorWaterCompanyInfoDao.count() == 0) {
            return null;
        }
        Specification<CountryMonitorWaterCompanyInfo> spec = Util.getDaySpecification(date);
        List<CountryMonitorWaterCompanyInfoDTO> countryMonitorWaterCompanyInfoArrayList = new ArrayList<>();
        Iterable<CountryMonitorWaterCompanyInfo> gwtWaterInfoIterable = countryMonitorWaterCompanyInfoDao.findAll(spec);
        for (CountryMonitorWaterCompanyInfo countryMonitorWaterCompanyInfo : gwtWaterInfoIterable) {
            CountryMonitorWaterCompanyInfoDTO companyInfoDTO = new CountryMonitorWaterCompanyInfoDTO();
            companyInfoDTO.setStationId(countryMonitorWaterCompanyInfo.getId());
            companyInfoDTO.setCreateTime(countryMonitorWaterCompanyInfo.getCreateTime());
            companyInfoDTO.setCompanyName(countryMonitorWaterCompanyInfo.getCompanyName());
            companyInfoDTO.setMonitorName(countryMonitorWaterCompanyInfo.getMonitorName());
            companyInfoDTO.setMonitorDate(countryMonitorWaterCompanyInfo.getMonitorDate());
            companyInfoDTO.setCodStandard(countryMonitorWaterCompanyInfo.getCodStandard());
            companyInfoDTO.setDateTime(countryMonitorWaterCompanyInfo.getCodLastAuditDate());
            companyInfoDTO.setCodLastAuditStatus(countryMonitorWaterCompanyInfo.getCodLastAuditStatus());
            companyInfoDTO.setCodDayMeanValue(countryMonitorWaterCompanyInfo.getCodDayMeanValue());
            companyInfoDTO.setNh4Standard(countryMonitorWaterCompanyInfo.getNh4Standard());
            companyInfoDTO.setNh4LastAuditStatus(countryMonitorWaterCompanyInfo.getNh4LastAuditStatus());
            companyInfoDTO.setNh4DayMeanValue(countryMonitorWaterCompanyInfo.getNh4DayMeanValue());
            companyInfoDTO.setX(countryMonitorWaterCompanyInfo.getX());
            companyInfoDTO.setY(countryMonitorWaterCompanyInfo.getY());

            countryMonitorWaterCompanyInfoArrayList.add(companyInfoDTO);
        }
        return countryMonitorWaterCompanyInfoArrayList;
    }


    /**
     * 获取30天水质信息
     * @param
     * @param date
     * @return
     */
    @Cacheable(value = "getCountryMonitorWaterCompanyInfo30d")
    public List<CountryMonitorWaterCompanyInfo> getCountryMonitorWaterCompanyInfo30d(String companyName,String monitorName, Date date) {
        List<CountryMonitorWaterCompanyInfo> countryMonitorWaterCompanyInfos = new ArrayList<>();
        Iterable<CountryMonitorWaterCompanyInfo> companyInfos = countryMonitorWaterCompanyInfoDao.getCountryMonitorWaterCompanyInfo30d(companyName,monitorName);
        for (CountryMonitorWaterCompanyInfo countryMonitorWaterCompanyInfo : companyInfos) {
            countryMonitorWaterCompanyInfos.add(countryMonitorWaterCompanyInfo);
        }
        return countryMonitorWaterCompanyInfos;
    }

}
