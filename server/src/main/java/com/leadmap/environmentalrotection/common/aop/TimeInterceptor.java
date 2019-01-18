package com.leadmap.environmentalrotection.common.aop;

import com.leadmap.environmentalrotection.service.InterfaceAccessService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Company: www.leadmap.net
 * Description:  检测方法执行耗时的spring切面类         记录一次请求中,流经的所有方法的调用耗时及次数
 *   使用@Aspect注解的类，Spring将会把它当作一个特殊的Bean（一个切面），也就是不对这个类本身进行动态代理
 *
 * @author: yxm
 * @Date: 2018/10/10 13:40
 */
@Aspect
@Component
public class TimeInterceptor {

    @Autowired
    private InterfaceAccessService interfaceAccessService;

    private static Log logger = LogFactory.getLog(TimeInterceptor.class);

    private Map<String, Integer> methodCount = new ConcurrentHashMap();

    /**
     *  一分钟，即60000ms
     */
    private static final long ONE_MINUTE = 60000;

    /**
     * controller层的统计耗时切面，类型必须为final String类型的,注解里要使用的变量只能是静态常量类型的
     */
    public static final String POINT = "execution (* com.leadmap.environmentalrotection.web.controller.*.*(..))";

    /**
     * 统计方法执行耗时Around环绕通知
     * @param joinPoint
     * @return
     */
    @Around(POINT)
    public Object timeAround(ProceedingJoinPoint joinPoint) {
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();

        try {
            obj = joinPoint.proceed(args);
        } catch (Throwable e) {
            logger.error("统计某方法执行耗时环绕通知出错", e);
        }

        // 获取执行的方法名
        long endTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        //获取访问次数
//        long count=interfaceAccessService.getCountByInterfaceAddress(methodName);

        long diffTime = endTime - startTime;
        interfaceAccessService.addInterfaceAccessInfo(methodName, diffTime);
        // 打印耗时的信息
        this.printExecTime(methodName, startTime, endTime);
        return obj;
    }

    /**
     * 打印方法执行耗时的信息，如果超过了一定的时间，才打印
     * @param methodName
     * @param startTime
     * @param endTime
     */
    private void printExecTime(String methodName, long startTime, long endTime) {
        long diffTime = endTime - startTime;
        System.out.println(methodName + " 方法执行耗时：" + diffTime + " ms");
        if (diffTime > ONE_MINUTE) {
            logger.warn("-----" + methodName + " 方法执行耗时：" + diffTime + " ms");
        }
    }

}
