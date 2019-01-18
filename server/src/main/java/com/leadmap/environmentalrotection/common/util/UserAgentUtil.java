package com.leadmap.environmentalrotection.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:判断是那个终端登录
 *
 * @author: yxm
 * @Date: 2018/11/1 13:42
 */
public class UserAgentUtil {

    private final static Logger logger = LoggerFactory.getLogger(UserAgentUtil.class);

    public static String getUserAgent(HttpServletRequest request) {
        try {
            String systemType;
            String info= request.getHeader("user-agent");
            logger.info("Create_Time : " + new Date());
            logger.info("用户设备详情 : " +info);
            if(info.contains("Android")) {
                System.out.println("Android移动客户端");
                systemType = "Android移动客户端";
            }else if(info.contains("CFNetwork")) {
                System.out.println("iPhone移动客户端");
                systemType = "iPhone移动客户端";
            }else {
                System.out.println("其他客户端");
                systemType = "其他客户端";
            }
            return systemType;
        } catch (Exception ex) {
            return null;
        }
    }

}
