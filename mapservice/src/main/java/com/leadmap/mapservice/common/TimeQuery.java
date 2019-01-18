package com.leadmap.mapservice.common;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;


/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2019/1/10 13:19
 */
public class TimeQuery {

    public static List<Predicate> getTimeQuery(List<Predicate> predicateList, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder, String time){

        if("今日".equals(time)){
            String beginDate = DateUtil.getTodayDate(1);
            String endDate = DateUtil.getTodayDate(0);
            Date beginTime = DateUtil.string2Date(beginDate, DateFormat.DateFull);
            Date endTime = DateUtil.string2Date(endDate, DateFormat.DateFull);
            predicateList.add(criteriaBuilder.greaterThan(root.get("updateTime"), beginTime));
            predicateList.add(criteriaBuilder.lessThan(root.get("updateTime"), endTime));
        }else if ("近七日".equals(time)){
            String beginDate = DateUtil.getTodayDate(7);
            String endDate = DateUtil.getTodayDate(0);
            Date beginTime = DateUtil.string2Date(beginDate, DateFormat.DateFull);
            Date endTime = DateUtil.string2Date(endDate, DateFormat.DateFull);
            predicateList.add(criteriaBuilder.greaterThan(root.get("updateTime"), beginTime));
            predicateList.add(criteriaBuilder.lessThan(root.get("updateTime"), endTime));
        }else if ("近一月".equals(time)){
            String beginDate = DateUtil.getTodayDate(30);
            String endDate = DateUtil.getTodayDate(0);
            Date beginTime = DateUtil.string2Date(beginDate, DateFormat.DateFull);
            Date endTime = DateUtil.string2Date(endDate, DateFormat.DateFull);
            predicateList.add(criteriaBuilder.greaterThan(root.get("updateTime"), beginTime));
            predicateList.add(criteriaBuilder.lessThan(root.get("updateTime"), endTime));
        }
        return predicateList;
    }
}
