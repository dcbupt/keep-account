package com.bupt.dc.control.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestController {

    /**
     * 全局跨域配置优先级高于这里
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        return "test";
    }
}
