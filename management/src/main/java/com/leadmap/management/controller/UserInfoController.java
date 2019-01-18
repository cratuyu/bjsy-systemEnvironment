package com.leadmap.management.controller;

import com.alibaba.druid.util.StringUtils;
import com.leadmap.management.model.OperationLog;
import com.leadmap.management.model.User;
import com.leadmap.management.model.UserInfo;
import com.leadmap.management.service.OperationLogService;
import com.leadmap.management.service.UserInfoService;
import com.leadmap.management.util.DateUtil;
import com.leadmap.management.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description: 用户管理
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@RestController
@RequestMapping("/userInfos")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 获取一周用户注册量
     * @return
     */
    @RequestMapping
    public String getAll(HttpServletRequest request){
        try {
            List<UserInfo> list = userInfoService.getUserRegister();
            return JSONUtil.writeValueAsString(list);
        }catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取用户注册量失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }


    /**
     * 获取一月用户注册量
     * @return
     */
    @RequestMapping(value = "/allUserInfo")
    public String getAllUserInfo(HttpServletRequest request){
        try {
            String time = request.getParameter("time");
            String times;
            if (!StringUtils.isEmpty(time)){
                times = time;
            }else {
                times = DateUtil.getPastDate(0);
            }
            List<UserInfo> list = userInfoService.getAllUserRegister(times);
            return JSONUtil.writeValueAsString(list);
        }catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取每月注册量失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }


    /**
     * 获取用户总的注册数
     * @return
     */
    @RequestMapping(value = "/allUser")
    public Integer getAllUser(HttpServletRequest request){
        try {
            Integer userCount = userInfoService.getAllUser();
            return userCount;
        }catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取用户总的注册数失败！");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }
}
