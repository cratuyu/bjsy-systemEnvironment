package com.leadmap.management.controller;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.IpInfo;
import com.leadmap.management.model.OperationLog;
import com.leadmap.management.model.User;
import com.leadmap.management.service.IpInfoService;
import com.leadmap.management.service.OperationLogService;
import com.leadmap.management.shiro.ShiroService;
import com.leadmap.management.util.DateFormat;
import com.leadmap.management.util.DateUtil;
import com.leadmap.management.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Company: www.leadmap.net
 * Description: 用户登录情况
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@RestController
@RequestMapping("/ipInfos")
public class IpInfoController {

    @Autowired
    private IpInfoService ipInfoService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ShiroService shiroService;

    @RequestMapping
    public Map<String,Object> getAll(IpInfo ipInfo, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length,HttpServletRequest request) {
        try {
            Map<String, Object> map = new HashMap<>();
            String userAgent = request.getParameter("userAgent");
            String time = request.getParameter("time");
            if (time != null && !"".equals(time)) {
                String endTime = DateUtil.getPastDate(0);
                String beginTime = DateUtil.getTodayDate(Integer.valueOf(time));
                Date beginTimes = null;
                Date endTimes = null;
                if (!StringUtils.isEmpty(beginTime)) {
                    beginTimes = DateUtil.string2Date(beginTime, DateFormat.DateFull);
                }
                if (!StringUtils.isEmpty(endTime)) {
                    endTimes = DateUtil.string2Date(endTime, DateFormat.DateFull);
                }
                PageInfo<IpInfo> pageInfo = ipInfoService.selectByIpInfo(ipInfo, beginTimes, endTimes, userAgent, start, length);
                System.out.println("pageInfo.getTotal():" + pageInfo.getTotal());
                map.put("draw", draw);
                map.put("recordsTotal", pageInfo.getTotal());
                map.put("recordsFiltered", pageInfo.getTotal());
                map.put("data", pageInfo.getList());
                return map;
            }
            PageInfo<IpInfo> pageInfo = ipInfoService.selectByPage(ipInfo, start, length);
            System.out.println("pageInfo.getTotal():" + pageInfo.getTotal());
            map.put("draw", draw);
            map.put("recordsTotal", pageInfo.getTotal());
            map.put("recordsFiltered", pageInfo.getTotal());
            map.put("data", pageInfo.getList());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取用户操作情况列表失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }


    /**
     * 获取一周日活
     * @return
     */
    @RequestMapping(value = "/weekIpInfo")
    public String getWeekIpInfoweekIpInfo(HttpServletRequest request){
        try {
            List<IpInfo> list = ipInfoService.getWeekIpInfo();
            return JSONUtil.writeValueAsString(list);
        }catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取本周日活失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }

    /**
     * 获取一月用户活跃度
     * @return
     */
    @RequestMapping(value = "/monthIpInfo")
    public String getMonthIpInfo(HttpServletRequest request){
        try {
            String time = request.getParameter("time");
            String times;
            if (!StringUtils.isEmpty(time)){
                times = time;
            }else {
                times = DateUtil.getPastDate(0);
            }
            List<IpInfo> list = ipInfoService.getMmonthIpInfo(times);
            return JSONUtil.writeValueAsString(list);
        }catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取月用户活跃失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }
}
