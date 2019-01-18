package com.leadmap.management.controller;

import com.leadmap.management.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Company: www.leadmap.net
 * Description:登录
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@Controller
public class HomeController {

    @RequestMapping(value="/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, User user, Model model){
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        Subject subject = SecurityUtils.getSubject();
        Cookie cookie = new Cookie("user",user.getUsername());
        response.addCookie(cookie);
        System.out.println(user);
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            subject.login(token);
            return "redirect:usersPage";
        }catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            return "login";
        }
    }
    @RequestMapping(value={"/usersPage",""})
    public String usersPage(){
        return "user/users";
    }

    @RequestMapping("/usersInfoPage")
    public String usersInfoPage(){
        return "user/usersInfo";
    }

    @RequestMapping("/rolesPage")
    public String rolesPage(){
        return "role/roles";
    }

    @RequestMapping("/resourcesPage")
    public String resourcesPage(){
        return "resources/resources";
    }

    @RequestMapping("/documentPage")
    public String documentPage(){
        return "document/document";
    }

    @RequestMapping("/opinionPage")
    public String opinionPage(){
        return "opinion/opinion";
    }

    @RequestMapping("/versionPage")
    public String versionPage(){
        return "version/version";
    }

    @RequestMapping("/ipInfoPage")
    public String ipInfoPage(){
        return "ipInfo/ipInfo";
    }

    @RequestMapping("/ipPage")
    public String ipPage(){
        return "ipInfo/ipInfos";
    }

    @RequestMapping("/interfacePage")
    public String interfacePage(){
        return "interface/interface";
    }

    @RequestMapping("/operationPage")
    public String operationPage(){
        return "operation/operation";
    }

    @RequestMapping("/welcome")
    public String forbidden(){
        return "welcome";
    }
}
