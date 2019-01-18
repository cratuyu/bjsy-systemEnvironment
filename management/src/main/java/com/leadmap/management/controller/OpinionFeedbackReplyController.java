package com.leadmap.management.controller;

import com.leadmap.management.model.OperationLog;
import com.leadmap.management.model.OpinionFeedbackInfoReply;
import com.leadmap.management.model.User;
import com.leadmap.management.service.OperationLogService;
import com.leadmap.management.service.OpinionFeedbackReplyService;
import com.leadmap.management.util.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Company: www.leadmap.net
 * Description: 意见反馈回复管理
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@RestController
@RequestMapping("/opinionsReply")
public class OpinionFeedbackReplyController {

    @Autowired
    private OpinionFeedbackReplyService opinionFeedbackReplyService;

    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping(value = "/addOpinionsReply")
    public String add(HttpServletRequest request ,HttpServletResponse response){
        try{
            String content = request.getParameter("content");
            String opFeedId = request.getParameter("opFeedId");
            String id = UUID.randomUUID().toString();
            Date date = new Date();

            OpinionFeedbackInfoReply reply = new OpinionFeedbackInfoReply();
            reply.setId(id);
            reply.setContent(content);
            reply.setOpFeedId(opFeedId);
            reply.setCreateTime(date);
            opinionFeedbackReplyService.addOpinionFeedbackReply(reply);

            Map<String, Object> map = new HashMap<>();
            map.put("id",id);
            map.put("opFeedId",opFeedId);
            map.put("content",content);
            map.put("date",date);
            HttpClientUtils.doPost("http://117.121.97.120:9001/bjsy-dc/lingtu/optionFeedbackReply/save",map);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("添加意见反馈回复失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }

}
