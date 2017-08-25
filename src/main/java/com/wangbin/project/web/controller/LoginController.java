package com.wangbin.project.web.controller;

import com.wangbin.project.base.business.entity.AdminUser;
import com.wangbin.project.base.business.service.AdminUserService;
import com.wangbin.project.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wangbin
 **/
@Controller
@RequestMapping("/adminUser")
public class LoginController extends AbstractController{

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {
        Response<Map<String,Object>> response = new Response<>();
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
//        token.setRememberMe(true);
        AdminUser adminUser = adminUserService.getAdminUserByName(userName);


//        response.setCode(0);
//        response.setMessage("登陆成功");
//        return toJsonResponse(response);
        return "index";
    }


}
