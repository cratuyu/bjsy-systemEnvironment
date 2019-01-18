package com.leadmap.management.controller;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.OperationLog;
import com.leadmap.management.model.User;
import com.leadmap.management.model.UserRole;
import com.leadmap.management.service.OperationLogService;
import com.leadmap.management.service.UserRoleService;
import com.leadmap.management.service.UserService;
import com.leadmap.management.util.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Company: www.leadmap.net
 * Description: 用户管理
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping
    public Map<String,Object> getAll(User user, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length,HttpServletRequest request){
        try {
            Map<String, Object> map = new HashMap<>();
            PageInfo<User> pageInfo = userService.selectByPage(user, start, length);
            System.out.println("pageInfo.getTotal():" + pageInfo.getTotal());
            map.put("draw", draw);
            map.put("recordsTotal", pageInfo.getTotal());
            map.put("recordsFiltered", pageInfo.getTotal());
            map.put("data", pageInfo.getList());
            return map;
        }catch (Exception e) {
            e.printStackTrace();
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取用户列表失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }


    /**
     * 保存用户角色
     * @param userRole 用户角色
     *  	  此处获取的参数的角色id是以 “,” 分隔的字符串
     * @return
     */
    @RequestMapping("/saveUserRoles")
    public String saveUserRoles(UserRole userRole,HttpServletRequest request){
        if(StringUtils.isEmpty(userRole.getUserid())){
            return "error";
        }
        try {
            userRoleService.addUserRole(userRole);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("保存用户角色失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }

    @RequestMapping(value = "/add")
    public String add(User user,HttpServletRequest request) {
        User u = userService.selectByUsername(user.getUsername());
        if(u != null){
            return "error";
        }
        try {
            user.setEnable(1);
            PasswordHelper passwordHelper = new PasswordHelper();
            passwordHelper.encryptPassword(user);
            userService.save(user);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("添加用户角色失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete(Integer id,HttpServletRequest request){
      try{
          userService.delUser(id);
          return "success";
      }catch (Exception e){
          e.printStackTrace();
          User user = (User) request.getSession().getAttribute("user");
          String userName = user.getUsername();
          OperationLog operationLog = new OperationLog();
          operationLog.setIp(request.getRemoteAddr());
          operationLog.setUserName(userName);
          operationLog.setLogSign("异常日志");
          operationLog.setLogName("删除用户角色失败");
          operationLog.setCreateTime(new Date());
          operationLogService.save(operationLog);
          return "fail";
      }
    }
}
