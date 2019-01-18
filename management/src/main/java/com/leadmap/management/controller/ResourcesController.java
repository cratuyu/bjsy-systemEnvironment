package com.leadmap.management.controller;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.OperationLog;
import com.leadmap.management.model.Resources;
import com.leadmap.management.model.User;
import com.leadmap.management.service.OperationLogService;
import com.leadmap.management.service.ResourcesService;
import com.leadmap.management.shiro.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Company: www.leadmap.net
 * Description: 资源管理
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@RestController
@RequestMapping("/resources")
public class ResourcesController {

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ShiroService shiroService;

    @RequestMapping
    public Map<String,Object> getAll(Resources resources, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length,HttpServletRequest request){

        try{
            Map<String,Object> map = new HashMap<>();
            PageInfo<Resources> pageInfo = resourcesService.selectByPage(resources, start, length);
            System.out.println("pageInfo.getTotal():" + pageInfo.getTotal());
            map.put("draw", draw);
            map.put("recordsTotal", pageInfo.getTotal());
            map.put("recordsFiltered", pageInfo.getTotal());
            map.put("data", pageInfo.getList());
            return map;
        }catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取资源列表失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }

    @RequestMapping("/resourcesWithSelected")
    public List<Resources> resourcesWithSelected(Integer rid){
        return resourcesService.queryResourcesListWithSelected(rid);
    }

    @RequestMapping("/loadMenu")
    public List<Resources> loadMenu(HttpServletRequest request){
        try {
            Map<String, Object> map = new HashMap<>();
            Integer userid = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
            map.put("userid", userid);
            List<Resources> resourcesList = resourcesService.loadUserResources(map);
            return resourcesList;
        } catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取菜单列表失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }

    @RequestMapping(value = "/add")
    public String add(Resources resources,HttpServletRequest request){
        try{
            resourcesService.save(resources);
            //更新权限
            shiroService.updatePermission();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("添加资源失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }
    @RequestMapping(value = "/delete")
    public String delete(Integer id,HttpServletRequest request){
        try{
            resourcesService.delete(id);
            //更新权限
            shiroService.updatePermission();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("删除资源失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }
}
