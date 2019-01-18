package com.leadmap.mapservice.service;

import com.leadmap.mapservice.common.DateFormat;
import com.leadmap.mapservice.common.DateUtil;
import com.leadmap.mapservice.dao.IpInfoDao;
import com.leadmap.mapservice.dao.UserInfoDao;
import com.leadmap.mapservice.dto.ResultObjInfo;
import com.leadmap.mapservice.dto.UsersDayActiveDto;
import com.leadmap.mapservice.dto.UsersDayAddDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/26 17:06
 */
@Component
public class UsersDayAddService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private IpInfoDao ipInfoDao;

    public ResultObjInfo<List<UsersDayAddDto>> getResult(String strDate ,int days) throws SQLException{
        ResultObjInfo<List<UsersDayAddDto>> resultObjInfo = new ResultObjInfo<>();
        List<UsersDayAddDto> list = new ArrayList<UsersDayAddDto>();
        if (strDate.isEmpty()) {
            for (int i = 0; i < days; i++) {
                UsersDayAddDto usersDayAddDto = new UsersDayAddDto();
                String today = DateUtil.getDateYYmmdd(i);
                Long count = getCounts(today, i);
                usersDayAddDto.setCreateTime(today);
                usersDayAddDto.setCount(count);
                list.add(usersDayAddDto);
            }
        } else {
            for (int i = 1; i <= days; i++) {
                UsersDayAddDto usersDayAddDto = new UsersDayAddDto();
                String today = DateUtil.getUpdateDate(strDate, i);
                Long count = getCounts(today, i);
                usersDayAddDto.setCreateTime(today);
                usersDayAddDto.setCount(count);
                list.add(usersDayAddDto);
            }
        }
        resultObjInfo.setCode("200");
        resultObjInfo.setMsg("success");
        resultObjInfo.setData(list);
        return resultObjInfo;
    }

    public Long getCounts (String today,int i){
        Date beginTime = DateUtil.string2Date(today + " 00:00:00", DateFormat.DateFull);
        Date endTime = DateUtil.string2Date(today + " 23:59:59", DateFormat.DateFull);
        long count = userInfoDao.getUserCountByCreateTime(beginTime, endTime);
        return count;
    }

    public ResultObjInfo<List<UsersDayActiveDto>> getActiveResult(String strDate, int days) throws SQLException {
        ResultObjInfo<List<UsersDayActiveDto>> resultObjInfo = new ResultObjInfo<>();
        List<UsersDayActiveDto> list = new ArrayList<UsersDayActiveDto>();
        if(strDate.isEmpty()){
            for(int i = 0; i < days; i++){
                UsersDayActiveDto usersDayActiveDto = new UsersDayActiveDto();
                String today = DateUtil.getDateYYmmdd(i);
                Long count = ipInfoDao.getIpDayActive(today);
                usersDayActiveDto.setCount(count);
                usersDayActiveDto.setCreateTime(today);
                list.add(usersDayActiveDto);
            }
        }else {
            for(int i = 1; i <= days; i++) {
                UsersDayActiveDto usersDayActiveDto = new UsersDayActiveDto();
                String today = DateUtil.getUpdateDate(strDate, i);
                Long count = getCounts(today, i);
                usersDayActiveDto.setCreateTime(today);
                usersDayActiveDto.setCount(count);
                list.add(usersDayActiveDto);
            }
        }
        resultObjInfo.setCode("200");
        resultObjInfo.setMsg("success");
        resultObjInfo.setData(list);
        return resultObjInfo;
    }
}
