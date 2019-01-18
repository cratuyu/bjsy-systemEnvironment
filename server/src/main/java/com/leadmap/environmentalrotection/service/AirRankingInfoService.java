package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.dao.AirRankingInfoDao;
import com.leadmap.environmentalrotection.entity.air.AirRankingInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/13 10:45
 */
@Component
public class AirRankingInfoService {

    private final static Logger logger = LoggerFactory.getLogger(AirRankingInfoService.class);

    @Value("${airPaimingUrl}")
    private String airPaimingUrl;

    @Autowired
    private AirRankingInfoDao airRankingInfoDao;

    @Cacheable(value = "getAirRankingInfo")
    public List<AirRankingInfo> getAirRankingInfo(PageRequest pageRequest) {
        if (airRankingInfoDao.count() == 0) {
            return null;
        }
        Date date = getNewestDate();
        Specification<AirRankingInfo> spec = new Specification<AirRankingInfo>() {
            @Override
            public Predicate toPredicate(Root<AirRankingInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("createTime"), date));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        List<AirRankingInfo> airRankingInfoList = new ArrayList<>();
        for (AirRankingInfo airRankingInfo : airRankingInfoDao.findAll(spec, pageRequest)) {
            airRankingInfoList.add(airRankingInfo);
        }
        return airRankingInfoList;
    }

    public long getAirRankingInfoCount() {
        Date date = getNewestDate();
        Specification<AirRankingInfo> spec = new Specification<AirRankingInfo>() {
            @Override
            public Predicate toPredicate(Root<AirRankingInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.equal(root.get("createTime"), date));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return airRankingInfoDao.count(spec);
    }

    public Date getNewestDate() {
        List<AirRankingInfo> gwtWaterInfoList = airRankingInfoDao.findTop1ByOrderByCreateTimeDesc();
        if (gwtWaterInfoList != null && gwtWaterInfoList.size() > 0) {
            return gwtWaterInfoList.get(0).getCreateTime();
        } else {
            return null;
        }
    }

}
