package com.leadmap.management.controller;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.OperationLog;
import com.leadmap.management.model.Role;
import com.leadmap.management.model.RoleResources;
import com.leadmap.management.model.User;
import com.leadmap.management.service.OperationLogService;
import com.leadmap.management.service.RoleResourcesService;
import com.leadmap.management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
 * Description: 角色管理
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleResourcesService roleResourcesService;

    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping
    public  Map<String,Object> getAll(Role role, String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length,HttpServletRequest request){
        try {
            Map<String, Object> map = new HashMap<>();
            PageInfo<Role> pageInfo = roleService.selectByPage(role, start, length);
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
            operationLog.setLogName("获取角色列表失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }

    @RequestMapping("/rolesWithSelected")
    public List<Role> rolesWithSelected(Integer uid){
        return roleService.queryRoleListWithSelected(uid);
    }

    /**
     * 分配角色
     * @param roleResources
     * @return
     */
    @RequestMapping("/saveRoleResources")
    public String saveRoleResources(RoleResources roleResources,HttpServletRequest request){
        if(StringUtils.isEmpty(roleResources.getRoleid())) {
            return "error";
        }
        try {
            roleResourcesService.addRoleResources(roleResources);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("分配角色失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }

    @RequestMapping(value = "/add")
    public String add(Role role,HttpServletRequest request) {
        try {
            roleService.save(role);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("添加角色失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete(Integer id,HttpServletRequest request){
        try{
            roleService.delRole(id);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("删除角色失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }



}
