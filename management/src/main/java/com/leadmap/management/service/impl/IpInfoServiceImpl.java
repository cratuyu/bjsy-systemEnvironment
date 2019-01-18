package com.leadmap.management.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leadmap.management.mapper.IpInfoMapper;
import com.leadmap.management.model.IpInfo;
import com.leadmap.management.service.IpInfoService;
import com.leadmap.management.util.DateFormat;
import com.leadmap.management.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@Service("ipInfoService")
public class IpInfoServiceImpl extends BaseService<IpInfo> implements IpInfoService {

    @Resource
    private IpInfoMapper ipInfoMapper;

    @Override
    public PageInfo<IpInfo> selectByIpInfo(IpInfo ipInfo, Date beginTimes, Date endTimes,String userAgent, int start, int length) {
        int page = start/length+1;
        //分页查询
        PageHelper.startPage(page, length);
        List<IpInfo> ipInfos = ipInfoMapper.selectByIpInfo(ipInfo,beginTimes,endTimes ,userAgent);
        return new PageInfo<>(ipInfos);
    }


    @Override
    public PageInfo<IpInfo> selectByPage(IpInfo ipInfo, int start, int length) {
        int page = start/length+1;
//        Example example = new Example(IpInfo.class);
//        Example.Criteria criteria = example.createCriteria();
////        if (StringUtil.isNotEmpty(ipInfo.getUserAgent())) {
////            criteria.andLike("userAgent", "%" + ipInfo.getUserAgent() + "%");
////        }
        String userAgent = ipInfo.getUserAgent();
        //分页查询
        PageHelper.startPage(page, length);
        List<IpInfo> ipInfos = ipInfoMapper.selectByIpInfos(userAgent);
        return new PageInfo<>(ipInfos);
    }

    @Override
    public List<IpInfo> getWeekIpInfo() {
        List<IpInfo> ipInfos = new ArrayList<>();
        for (int i = 6; i >=0; i--) {
            String beginTime = DateUtil.getTodayDate(i+1);
            String endTime = DateUtil.getTodayDate(i);
            String nowTime = DateUtil.getDateYYmmdd(i+1);
            Date beginTimes = null;
            Date endTimes = null;
            if (!StringUtils.isEmpty(beginTime)) {
                beginTimes = DateUtil.string2Date(beginTime, DateFormat.DateFull);
            }
            if (!StringUtils.isEmpty(endTime)) {
                endTimes = DateUtil.string2Date(endTime, DateFormat.DateFull);
            }
            Integer list= ipInfoMapper.getWeekIpInfo(beginTimes,endTimes);
            if(list == null){
                list = 0;
            }
            IpInfo ipInfo = new IpInfo();
            ipInfo.setTime(nowTime);
            ipInfo.setCount(list);
            ipInfos.add(ipInfo);
        }
        return ipInfos;
    }

    @Override
    public List<IpInfo> getMmonthIpInfo(String time) {
        Integer day = DateUtil.getdayOfMonth(time);
        List<IpInfo> ipInfos = new ArrayList<>();
        for (int i = 1; i<=day; i++) {
            String  beginTime = DateUtil.getWillDate(time,i);
            String endTime = DateUtil.getWillDate(time,i+1);
            String updateTime = DateUtil.getUpdateDate(time,i);
            Date beginTimes = null;
            Date endTimes = null;
            if (!StringUtils.isEmpty(beginTime)) {
                beginTimes = DateUtil.string2Date(beginTime, DateFormat.DateFull);
            }
            if (!StringUtils.isEmpty(endTime)) {
                endTimes = DateUtil.string2Date(endTime, DateFormat.DateFull);
            }
            Integer list= ipInfoMapper.getMmonthIpInfo(beginTimes,endTimes);
            if(list == null){
                list = 0;
            }
            IpInfo ipInfo = new IpInfo();
            ipInfo.setTime(updateTime);
            ipInfo.setCount(list);
            ipInfos.add(ipInfo);
        }
        return ipInfos;
    }

}
