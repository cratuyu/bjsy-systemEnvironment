package com.leadmap.management.controller;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.InterfaceAccess;
import com.leadmap.management.model.OperationLog;
import com.leadmap.management.model.User;
import com.leadmap.management.service.InterfaceService;
import com.leadmap.management.service.OperationLogService;
import com.leadmap.management.shiro.ShiroService;
import com.leadmap.management.util.DateFormat;
import com.leadmap.management.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Company: www.leadmap.net
 * Description: 接口访问量统计
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@RestController
@RequestMapping("/interfaces")
public class InterfaceController {

    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ShiroService shiroService;

    @RequestMapping
    public Map<String,Object> getAll(InterfaceAccess interfaceAccess, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length,HttpServletRequest request) {
        try {
            Map<String, Object> map = new HashMap<>();
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
                PageInfo<InterfaceAccess> pageInfo = interfaceService.selectByInterface(interfaceAccess, beginTimes, endTimes, start, length);
                System.out.println("pageInfo.getTotal():" + pageInfo.getTotal());
                map.put("draw", draw);
                map.put("recordsTotal", pageInfo.getTotal());
                map.put("recordsFiltered", pageInfo.getTotal());
                map.put("data", pageInfo.getList());
                return map;
            }
            PageInfo<InterfaceAccess> pageInfo = interfaceService.selectByPage(interfaceAccess, start, length);
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
            operationLog.setLogName("获取接口访问情况列表失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }

}
