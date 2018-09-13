package com.crazyfish.kamal.controller;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by kamal on 15/8/3.
 */
@Controller
public class TestController {
    @RequestMapping("/test/login")
    public String testLogin(){

        return "/index";
    }

    @RequestMapping("/test/login1")
    public ModelAndView testLogin1(){
        ModelAndView view = new ModelAndView("index");
        view.setViewName("redirect:../index.jsp");
        Map map = Maps.newHashMap();
        map.put("yy", 33);
        map.put("yxx", 44);
        view.addObject("map", map);
        return view;
    }

    @RequestMapping("/login")
    public ModelAndView index(){
        ModelAndView view = new ModelAndView();
        view.setViewName("/index");
        Map map = Maps.newHashMap();
        map.put("yy", 33);
        map.put("yxx", 44);
        view.addObject("map", map);
        return view;
    }

    @RequestMapping("/get")
    @ResponseBody
    public String index1(){
        return "xxxxxxxx";
    }
}
