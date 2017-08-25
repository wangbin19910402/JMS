package com.wangbin.project.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * @author wangbin
 **/
@Controller
public class CommonController extends AbstractController {

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(HttpServletResponse response) {
        response.setStatus(500);
        return  "error";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(HttpServletResponse response) {
        return "welcome";
    }

    @RequestMapping(value = "/miss", method = RequestMethod.GET)
    public String miss(HttpServletResponse response) {
        response.setStatus(404);
        return "miss";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletResponse response) {
        return "index";
    }
}
