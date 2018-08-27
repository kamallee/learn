package com.crazyfish.kamal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kamal on 15/8/3.
 */
@Controller
public class TestController {
    @RequestMapping("/test/login")
    public String testLogin(@RequestParam(value="username")String username,String password,HttpServletRequest request){
        System.out.println("xxx" + username + "yy" + password);
        if( !"admin".equals(username) && !"admin".equals(password)){
            return "failure";
        }
        return "redirect:../index.jsp";
    }
}
