package com.leadmap.mapservice.controller;

import com.leadmap.mapservice.dao.UserDao;
import com.leadmap.mapservice.dao.UserInfoDao;
import com.leadmap.mapservice.entity.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/21 10:07
 */
@Controller
public class LoginController {

    private static final String USERNAME = "username";
    private String algorithmName = "md5";
    private int hashIterations = 2;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/")
    public String root(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute(USERNAME) == null) {
            return "login";
        }else {
//            String username = (String) httpSession.getAttribute(USERNAME);
//            if (USERNAME.equals(username)) {
//                request.getSession().setAttribute(USERNAME, username);
//                return "index";
//            }
            return "index";
        }
    }

    @RequestMapping(value = "/loginSubmit",method = RequestMethod.POST )
    public String root(@RequestParam(value="username")String username,
                       @RequestParam(value="password")String password,
                       HttpServletRequest request, HttpServletResponse response){
        String md5Password = new SimpleHash(algorithmName, password,  ByteSource.Util.bytes(username), hashIterations).toHex();
        User user = userDao.findByUsernameAndPassword(username,md5Password);
        if(user != null ){
            request.getSession().setAttribute(USERNAME, username);
            return "index";
        }
        return "login";
    }

    @RequestMapping(value = "/loginOut")
    public String loginout( HttpServletRequest request,  HttpServletResponse response){
        request.getSession().removeAttribute(USERNAME);
        return "login";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
