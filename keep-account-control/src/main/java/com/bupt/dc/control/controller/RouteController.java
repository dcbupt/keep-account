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

    /**
     * 全局跨域配置优先级高于这里,这种配置方式对spring security控制的访问路径下的跨域问题不生效
     * @return
     */
    //@CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
