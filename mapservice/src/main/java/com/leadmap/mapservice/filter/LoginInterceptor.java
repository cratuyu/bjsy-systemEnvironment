package com.leadmap.mapservice.filter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2019/1/8 15:58
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final String LOGIN = "/login";

    private static final String USERNAME = "username";

    private static final String LOGINSUBMIT = "/loginSubmit";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        String url = request.getRequestURL().toString();
        if(!LOGIN.equals(url.substring(url.length() - 6, url.length()))) {
            if (LOGINSUBMIT.equals(url.substring(url.length() - 12, url.length()))){
              return true;
            }
            else if (httpSession.getAttribute(USERNAME) == null) {
                response.sendRedirect(request.getContextPath() + LOGIN);
                return false;
            } else {
                String username = (String) httpSession.getAttribute(USERNAME);
                if (USERNAME.equals(username)) {
                    request.getSession().setAttribute(USERNAME, username);
                    return true;
                }
            }
        }else if (LOGIN.equals(url.substring(url.length() - 6, url.length()))){
            return true;
        }
        return true;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        //
//    }

}
