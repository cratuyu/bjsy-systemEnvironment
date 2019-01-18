package com.leadmap.mapservice.service;

import com.leadmap.mapservice.common.DateFormat;
import com.leadmap.mapservice.common.DateUtil;
import com.leadmap.mapservice.common.TimeQuery;
import com.leadmap.mapservice.common.TwoTuple;
import com.leadmap.mapservice.dao.InterfaceAccessDao;
import com.leadmap.mapservice.dao.IpInfoDao;
import com.leadmap.mapservice.dto.ResultInfo;
import com.leadmap.mapservice.entity.InterfaceAccess;
import com.leadmap.mapservice.entity.IpInfo;
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
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/28 9:47
 */
@Service
public class UserService {

    @Autowired
    private IpInfoDao ipInfoDao;

    @Autowired
    private InterfaceAccessDao interfaceAccessDao;

    public TwoTuple<List<InterfaceAccess>, Integer> getPageUserOperationInfo(int start, int count, String time){
        Pageable page = new PageRequest(start, count);
        Specification querySpecifi = new Specification<InterfaceAccess>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<>();
                predicatesList = TimeQuery.getTimeQuery(predicatesList,root,criteriaQuery,criteriaBuilder,time);
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
                criteriaQuery.where(criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()])));
                return criteriaQuery.getRestriction();
            }
        };
        List<InterfaceAccess> list = new ArrayList<>();
        list = interfaceAccessDao.findAll(querySpecifi,page).getContent();
        Integer counts = interfaceAccessDao.findAll(querySpecifi).size() / 10;
        return new TwoTuple<List<InterfaceAccess>, Integer> (list, counts);
    }

    public ResultInfo<List<IpInfo>> getPageIpInfo(int start, String time, String agent){
        ResultInfo<List<IpInfo>> resultInfo = new ResultInfo<>();
        start = start * 10;
        List<IpInfo> list = new ArrayList<>();
        Long recordCount = new Long(0);
        if (time.isEmpty() && agent.isEmpty()) {
            list =  ipInfoDao.getAllByFlag(start);
            recordCount = ipInfoDao.getCount() / 10;
        }else if (agent.isEmpty() && !time.isEmpty()){
            if ("今日".equals(time)){
                list = selectByTime(start, 1,0);
                recordCount = getCount(1,0) / 10;
            }else if ("近七日".equals(time)){
                list = selectByTime(start, 7, 0);
                recordCount = getCount(7,0) / 10;
            }else if ("近一月".equals(time)){
                list = selectByTime(start, 30, 0);
                recordCount = getCount(30, 0) / 10;
            }
        }else if (time.isEmpty() && !agent.isEmpty()){
            list = ipInfoDao.getByAgent(agent,start);
            recordCount = ipInfoDao.getAgentCount(agent) / 10;
        }else if (!time.isEmpty() && !agent.isEmpty()){
            if ("今日".equals(time)){
                list = selectByTimeAndAgent(start, 1,0,agent);
                recordCount = getTimeAndAgentCount(1,0,agent) / 10;
            }else if ("近七日".equals(time)){
                list = selectByTimeAndAgent(start, 7, 0,agent);
                recordCount = getTimeAndAgentCount(7,0,agent) / 10;
            }else if ("近一月".equals(time)){
                list = selectByTimeAndAgent(start, 30, 0,agent);
                recordCount = getTimeAndAgentCount(30, 0,agent) / 10;
            }
        }
        resultInfo.setRecordCount(recordCount);
        resultInfo.setData(list);
        return resultInfo;
    }

    public Long getCount(int startDay, int endDay){
        String today = DateUtil.getTodayDate(startDay);
        String tomorrow = DateUtil.getTodayDate(endDay);
        Date begintime = DateUtil.string2Date(today, DateFormat.DateFull);
        Date endtime = DateUtil.string2Date(tomorrow,DateFormat.DateFull);
        return ipInfoDao.getTimeCount(begintime, endtime);
    }

    public Long getTimeAndAgentCount(int startDay, int endDay, String agent){
        String today = DateUtil.getTodayDate(startDay);
        String tomorrow = DateUtil.getTodayDate(endDay);
        Date begintime = DateUtil.string2Date(today, DateFormat.DateFull);
        Date endtime = DateUtil.string2Date(tomorrow,DateFormat.DateFull);
        return ipInfoDao.getAgentAndTimeCount(begintime, endtime,agent);
    }

    public List<IpInfo> selectByTime(int start, int startDay, int endDay){
        String today = DateUtil.getTodayDate(startDay);
        String tomorrow = DateUtil.getTodayDate(endDay);
        System.out.println(today);
        System.out.println(tomorrow);
        Date begintime = DateUtil.string2Date(today, DateFormat.DateFull);
        Date endtime = DateUtil.string2Date(tomorrow,DateFormat.DateFull);

        return ipInfoDao.getByTime(begintime, endtime, start);
    }

    public List<IpInfo> selectByTimeAndAgent(int start, int startDay, int endDay, String agent){
        String today = DateUtil.getTodayDate(startDay);
        String tomorrow = DateUtil.getTodayDate(endDay);
        System.out.println(today);
        System.out.println(tomorrow);
        Date begintime = DateUtil.string2Date(today, DateFormat.DateFull);
        Date endtime = DateUtil.string2Date(tomorrow,DateFormat.DateFull);
        return ipInfoDao.getByAgentAndTime(begintime, endtime, agent,start);
    }
}
