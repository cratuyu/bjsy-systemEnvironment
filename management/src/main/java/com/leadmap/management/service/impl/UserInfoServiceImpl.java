package com.leadmap.management.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.leadmap.management.mapper.UserInfoMapper;
import com.leadmap.management.model.UserInfo;
import com.leadmap.management.service.UserInfoService;
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
@Service("userInfoService")
public class UserInfoServiceImpl extends BaseService<UserInfo> implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;


    @Override
    public List<UserInfo> getUserRegister() {
        List<UserInfo> userInfos = new ArrayList<>();
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
            List<UserInfo> list= userInfoMapper.getUserRegister(beginTimes,endTimes);
            UserInfo userInfo = new UserInfo();
            userInfo.setUpdateTime(nowTime);
            userInfo.setCount(list.size());
            userInfos.add(userInfo);
        }
        return userInfos;
    }

    @Override
    public List<UserInfo> getAllUserRegister(String time) {
        Integer day = DateUtil.getdayOfMonth(time);
        List<UserInfo> userInfos = new ArrayList<>();
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
            List<UserInfo> list= userInfoMapper.getUserRegister(beginTimes,endTimes);
            UserInfo userInfo = new UserInfo();
            userInfo.setUpdateTime(updateTime);
            userInfo.setCount(list.size());
            userInfos.add(userInfo);
        }
        return userInfos;
    }

    @Override
    public Integer getAllUser() {

        Integer list= userInfoMapper.getAllUser();
        if(list == null){
            list = 0;
        }
        return list;
    }
}
