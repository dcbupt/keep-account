package com.bupt.dc.control.controller;

import com.bupt.dc.object.exception.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {

    @RequestMapping("/myException")
    public String myException() throws MyException {
        throw new MyException("发生错误");
    }

}
