package com.bupt.dc.object.exception;

import com.bupt.dc.object.error.ErrorCodeConstant;
import com.bupt.dc.object.error.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception {
        ErrorInfo r = new ErrorInfo(ErrorCodeConstant.UNKNOWN_ERROR, e.getMessage());
        r.setUrl(req.getRequestURL().toString());
        return r;
    }

}
