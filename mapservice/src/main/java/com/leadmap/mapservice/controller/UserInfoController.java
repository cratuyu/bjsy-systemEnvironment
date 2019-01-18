package com.leadmap.mapservice.controller;

import com.leadmap.mapservice.common.DateUtil;
import com.leadmap.mapservice.dao.UserInfoDao;
import com.leadmap.mapservice.dto.ResultObjInfo;
import com.leadmap.mapservice.dto.UsersDayActiveDto;
import com.leadmap.mapservice.dto.UsersDayAddDto;
import com.leadmap.mapservice.service.UsersDayAddService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/26 11:12
 */
@Controller
public class UserInfoController {

    private final static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UsersDayAddService usersDayAddService;

    @RequestMapping(value = "/allUsers", method = RequestMethod.POST)
    @ResponseBody
    public String getAllUsers(){
        try {
            String count = userInfoDao.getAllUsers();
            return count;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value = "/usersDayAdd",method = RequestMethod.POST)
    @ResponseBody
    public ResultObjInfo<List<UsersDayAddDto>> usersDayAdd(){
        ResultObjInfo<List<UsersDayAddDto>> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo = usersDayAddService.getResult("",7);
        }catch (Exception e){
            e.printStackTrace();
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
            resultObjInfo.setData(null);
        }
        return resultObjInfo;
    }

    @RequestMapping(value = "usersMonthAdd", method = RequestMethod.POST)
    @ResponseBody
    public ResultObjInfo<List<UsersDayAddDto>> usersMonthAdd(HttpServletRequest request) {
        ResultObjInfo<List<UsersDayAddDto>> resultObjInfo = new ResultObjInfo<>();
        String strDate = request.getParameter("time");
        try {
            if (strDate.isEmpty()) {
                SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM");
                Date dNow = new Date();
                strDate = simdf.format(dNow);
            }
            int days = DateUtil.getdayOfMonth(strDate);
            resultObjInfo = usersDayAddService.getResult(strDate, days);
        }catch (Exception e){
            e.printStackTrace();
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
            resultObjInfo.setData(null);
        }
        return resultObjInfo;
    }


    @RequestMapping(value = "/usersDayActive", method = RequestMethod.POST)
    @ResponseBody
    public ResultObjInfo<List<UsersDayActiveDto>> usersDayActive(){
        ResultObjInfo<List<UsersDayActiveDto>> resultObjInfo = new ResultObjInfo<>();
        try{
            resultObjInfo  = usersDayAddService.getActiveResult("",7);
        }catch (Exception ex){
            ex.printStackTrace();
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
            resultObjInfo.setData(null);
        }
        return resultObjInfo;
    }

    @RequestMapping(value = "/usersMonthActive", method = RequestMethod.POST)
    @ResponseBody
    public ResultObjInfo<List<UsersDayActiveDto>> usersMonthActive(HttpServletRequest request){
        ResultObjInfo<List<UsersDayActiveDto>> resultObjInfo = new ResultObjInfo<>();
        String strDate = request.getParameter("time");
        try {
            if(strDate.isEmpty()) {
                SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM");
                Date dNow = new Date();
                strDate = simdf.format(dNow);
            }
            int days = DateUtil.getdayOfMonth(strDate);
            resultObjInfo = usersDayAddService.getActiveResult(strDate, days);
        }catch (Exception ex){
            ex.printStackTrace();
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
            resultObjInfo.setData(null);
        }
        return resultObjInfo;
    }
}
