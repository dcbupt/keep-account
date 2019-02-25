package com.bupt.dc.control.controller;

import com.bupt.dc.config.properties.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面路由Controller
 */
@Controller
public class RouteController {

    @Autowired
    private SystemProperties systemProperties;

    @GetMapping({"/", "/index", "/home"})
    public String root(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
