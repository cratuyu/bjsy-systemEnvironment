package com.leadmap.management.controller;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.OperationLog;
import com.leadmap.management.model.OpinionFeedback;
import com.leadmap.management.model.User;
import com.leadmap.management.service.OperationLogService;
import com.leadmap.management.service.OpinionFeedbackService;
import com.leadmap.management.shiro.ShiroService;
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
 * Description: 意见反馈管理
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@RestController
@RequestMapping("/opinions")
public class OpinionFeedbackController {

    @Autowired
    private OpinionFeedbackService opinionFeedbackService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private ShiroService shiroService;

    @RequestMapping
    public Map<String,Object> getAll(OpinionFeedback opinionFeedback, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length,HttpServletRequest request){
        try {
            Map<String, Object> map = new HashMap<>();
            PageInfo<OpinionFeedback> pageInfo = opinionFeedbackService.selectByPage(opinionFeedback, start, length);
            System.out.println("pageInfo.getTotal():" + pageInfo.getTotal());
            map.put("draw", draw);
            map.put("recordsTotal", pageInfo.getTotal());
            map.put("recordsFiltered", pageInfo.getTotal());
            map.put("data", pageInfo.getList());
            return map;
        } catch (Exception e) {
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取意见反馈列表失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }

    @RequestMapping(value = "/add")
    public String add(OpinionFeedback opinionFeedback,HttpServletRequest request){
        try{
            opinionFeedbackService.save(opinionFeedback);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("添加意见反馈失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete(String id,HttpServletRequest request){
        try{
            opinionFeedbackService.delete(id);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("删除意见反馈失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }
}
