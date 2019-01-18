package com.leadmap.mapservice.controller;

import com.leadmap.mapservice.common.TwoTuple;
import com.leadmap.mapservice.dao.InterfaceAccessDao;
import com.leadmap.mapservice.dao.IpInfoDao;
import com.leadmap.mapservice.dao.UserOperationDao;
import com.leadmap.mapservice.dto.ResultInfo;
import com.leadmap.mapservice.entity.*;
import com.leadmap.mapservice.service.IpInfoService;
import com.leadmap.mapservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/20 15:20
 */

@Controller
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private IpInfoDao ipInfoDao;

    @Autowired
    private IpInfoService ipInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserOperationDao userOperationDao;

    @Autowired
    private InterfaceAccessDao interfaceAccessDao;

    @RequestMapping(value = "/userActive")
    public String userActive(){
        return "userActive";
    }

    /**
     * 将user_name 字段中不为空的数据返回
     */

    @ResponseBody
    @RequestMapping(value = "/ipInfoList", method = RequestMethod.POST)
    public ResultInfo<List<IpInfo>> getAllIpInfo(HttpServletRequest request){
        ResultInfo<List<IpInfo>> resultInfo = new ResultInfo<>();
        try {
            String time = request.getParameter("time");
            String agent = request.getParameter("agent");
            int pageNum = Integer.valueOf(request.getParameter("pageNum"));
//            resultInfo = userService.getPageIpInfo(pageNum,time, agent);
            TwoTuple<List<IpInfo>, Integer> tuple = ipInfoService.getPageIpinfo(pageNum, 10, time, agent);
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setData(tuple.getFirst());
            resultInfo.setRecordCount(tuple.getSecond());
            return resultInfo;
        }catch (Exception e){
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("username");
            String userName = user.getUsername();
            UserOperation operationLog = new UserOperation();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取用户登录详情失败");
            operationLog.setCreateTime(new Date());
            userOperationDao.save(operationLog);
            resultInfo.setData(null);
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setRecordCount(0);
            return resultInfo;
        }
    }

    @RequestMapping(value = "/userOperation")
    public String userOpeeration(){
        return "userOperation";
    }

    @ResponseBody
    @RequestMapping(value = "/userOperationList", method = RequestMethod.POST)
    public ResultInfo<List<InterfaceAccess>> userOperationList(HttpServletRequest request){
        ResultInfo<List<InterfaceAccess>> resultInfo = new ResultInfo<>();
        try {
            int pageNum = Integer.valueOf(request.getParameter("pageNum"));
            String time = request.getParameter("time");
            TwoTuple<List<InterfaceAccess>, Integer> tuple = userService.getPageUserOperationInfo(pageNum, 10, time);
            resultInfo.setData(tuple.getFirst());
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setRecordCount(tuple.getSecond());
            return resultInfo;
        }catch (Exception e){
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            UserOperation operationLog = new UserOperation();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取用户操作详情失败");
            operationLog.setCreateTime(new Date());
            userOperationDao.save(operationLog);
            resultInfo.setData(null);
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setRecordCount(0);
            return resultInfo;
        }
    }
}
