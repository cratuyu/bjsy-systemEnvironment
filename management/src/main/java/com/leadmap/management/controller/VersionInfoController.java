package com.leadmap.management.controller;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.OperationLog;
import com.leadmap.management.model.User;
import com.leadmap.management.model.VersionInfo;
import com.leadmap.management.service.OperationLogService;
import com.leadmap.management.service.VersionInfoService;
import com.leadmap.management.shiro.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Company: www.leadmap.net
 * Description: 资源管理
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@RestController
@RequestMapping("/versions")
public class VersionInfoController {

    @Autowired
    private VersionInfoService versionInfoService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ShiroService shiroService;

    @RequestMapping
    public Map<String,Object> getAll(VersionInfo versionInfo, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length,HttpServletRequest request){
     try{
        Map<String,Object> map = new HashMap<>();
        PageInfo<VersionInfo> pageInfo = versionInfoService.selectByPage(versionInfo, start, length);
        System.out.println("pageInfo.getTotal():"+pageInfo.getTotal());
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
     }catch (Exception e){
         e.printStackTrace();
         User user = (User) request.getSession().getAttribute("user");
         String userName = user.getUsername();
         OperationLog operationLog = new OperationLog();
         operationLog.setIp(request.getRemoteAddr());
         operationLog.setUserName(userName);
         operationLog.setLogSign("异常日志");
         operationLog.setLogName("获取版本列表失败");
         operationLog.setCreateTime(new Date());
         operationLogService.save(operationLog);
         return null;
     }
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request ,HttpServletResponse response){
        try{
            String versionName = request.getParameter("versionName");
            String versionPlatform = request.getParameter("versionPlatform");
            String isForceUpdata = request.getParameter("isForceUpdata");
            String versionDesc = request.getParameter("versionDesc");
            String versionUrl = request.getParameter("versionUrl");

            VersionInfo versionInfo = new VersionInfo();
            versionInfo.setVersionName(versionName);
            versionInfo.setVersionPlatform(versionPlatform);
            versionInfo.setIsForceUpdata(isForceUpdata);
            versionInfo.setVersionDesc(versionDesc);
            versionInfo.setVersionUrl(versionUrl);
            versionInfo.setUpdateTime(new Date());
            versionInfoService.insertVersionInfo(versionInfo);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("添加版本失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete(long id,HttpServletRequest request){
        try{
            versionInfoService.delete(id);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("删除版本失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }
}
