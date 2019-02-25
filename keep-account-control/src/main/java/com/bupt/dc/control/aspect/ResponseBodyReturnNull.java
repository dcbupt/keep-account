package com.bupt.dc.control.aspect;

import com.bupt.dc.control.annotation.ReturnNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;

@ControllerAdvice
@Slf4j
public class ResponseBodyReturnNull implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        AnnotatedElement annotatedElement = returnType.getAnnotatedElement();
        ReturnNull responseToJackson = AnnotationUtils.findAnnotation(annotatedElement, ReturnNull.class);
        return responseToJackson != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return null;
    }

}
