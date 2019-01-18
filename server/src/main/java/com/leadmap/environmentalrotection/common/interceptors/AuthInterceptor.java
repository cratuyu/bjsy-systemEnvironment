package com.leadmap.environmentalrotection.common.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/5/17 9:56
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
        // System.out.println("afterCompletion---");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
        // System.out.println("postHandle---");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        return true;
//        Admin tableinfo = (Admin) request.getSession().getAttribute("admin");
//        String requestURI = request.getRequestURI();
//        String loginUrl = "/login.jhtml";
//        String uri = requestURI.substring(requestURI.lastIndexOf("/"));
//        if (tableinfo == null) {
//            if (uri.startsWith("/login") || uri.startsWith("/imageLogin") || uri.startsWith("/checkLogin")) {
//                return true;
//            } else {
//                // 非法请求 重定向到登录页面
//                response.sendRedirect(request.getContextPath() + loginUrl);
//                return false;
//            }
//        } else {
//            // 添加日志
//            String ip = request.getRemoteAddr();
//            Long adminId = tableinfo.getId();
//            Log log = new Log();
//            log.setAdminId(adminId);
//            log.setIp(ip);
//            ThreadLocalUtils.set(log);
//            return true;
//        }
    }
}
