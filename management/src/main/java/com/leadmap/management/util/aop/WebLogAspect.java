package com.leadmap.management.util.aop;

import com.leadmap.management.model.OperationLog;
import com.leadmap.management.model.User;
import com.leadmap.management.service.OperationLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;

/**
 * Company: www.leadmap.net
 * Description: springboot集成AOP管理日志
 *
 * @author: yxm
 * @Date: 2018/11/5 13:28
 */
@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OperationLogService operationLogService;

    @Pointcut("execution(public * com.leadmap.management.controller..*.*(..))")
    public void webLog(){

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法名
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        String str = "com.leadmap.management.controller.HomeController.login";
        String userName = null;
        if (!methodName.equals(str)){
            User user = (User) request.getSession().getAttribute("user");
            userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setCreateTime(new Date());
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setMethod(request.getMethod());
            operationLog.setMethodName(methodName);
            operationLog.setUserName(userName);
            operationLog.setUrl(request.getRequestURL().toString());
            operationLog.setLogSign("操作日志");
            try {
                operationLogService.save(operationLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 记录下请求内容
        logger.info("userName : " + userName);
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("Create_Time : " + new Date());
        logger.info("methodName : " + methodName);
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            logger.info("name:{},value:{}", name, request.getParameter(name));
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }

}
