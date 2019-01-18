package com.leadmap.mapservice.service;

import com.leadmap.mapservice.common.TwoTuple;
import com.leadmap.mapservice.dao.IpInfoDao;
import com.leadmap.mapservice.entity.IpInfo;
import com.leadmap.mapservice.common.TimeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2019/1/10 10:25
 */
@Service
public class IpInfoService {

    @Autowired
    private IpInfoDao ipInfoDao;

    public TwoTuple<List<IpInfo>, Integer> getPageIpinfo(int start, int count, String time, String userAgent){
        Pageable page = new PageRequest(start, count);
        List<IpInfo> list = new ArrayList<>();
        Specification querySpecifi = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<>();
                predicatesList = TimeQuery.getTimeQuery(predicatesList,root,criteriaQuery,criteriaBuilder,time);
                if(!userAgent.isEmpty()){
                    predicatesList.add(criteriaBuilder.equal(root.get("userAgent"),userAgent));
                }
                predicatesList.add(criteriaBuilder.equal(root.get("flag"), 2));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
                criteriaQuery.where(criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()])));
                return criteriaQuery.getRestriction();
            }
        };
        list = ipInfoDao.findAll(querySpecifi,page).getContent();
        Integer counts = ipInfoDao.findAll(querySpecifi).size() / 10;
        System.out.println(counts);
        return new TwoTuple<List<IpInfo>, Integer>(list, counts);
    }


}
