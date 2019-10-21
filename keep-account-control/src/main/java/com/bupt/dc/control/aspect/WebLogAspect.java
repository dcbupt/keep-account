package com.bupt.dc.control.aspect;

import com.alibaba.fastjson.JSONObject;
import com.bupt.dc.object.util.JacksonUtil;
import com.mongodb.BasicDBObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Order(5)
@Component
public class WebLogAspect {

    private static final Logger log = LoggerFactory.getLogger("mongoLogger");

    private static final Logger basicLog = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.bupt.dc.control.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        BasicDBObject logInfo = getBasicDBObject4Request(request, joinPoint);
        log.info(JSONObject.toJSONString(logInfo));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        Map<String, String> map = new HashMap<>(2);
        map.put("RESPONSE", JacksonUtil.encode(ret));
        map.put("SPEND TIME", String.valueOf((System.currentTimeMillis() - startTime.get())));
        BasicDBObject logInfo = getBasicDBObjectByMap(map);
        log.info(JacksonUtil.encode(logInfo));
    }

    private BasicDBObject getBasicDBObject4Request(HttpServletRequest request, JoinPoint joinPoint) {
        // 基本信息
        BasicDBObject r = new BasicDBObject();
        r.append("requestURL", request.getRequestURL().toString());
        r.append("requestURI", request.getRequestURI());
        r.append("queryString", request.getQueryString());
        r.append("remoteAddr", request.getRemoteAddr());
        r.append("remoteHost", request.getRemoteHost());
        r.append("remotePort", request.getRemotePort());
        r.append("localAddr", request.getLocalAddr());
        r.append("localName", request.getLocalName());
        r.append("method", request.getMethod());
        r.append("headers", getHeadersInfo(request));
        r.append("parameters", request.getParameterMap());
        r.append("classMethod", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        r.append("args", Arrays.toString(joinPoint.getArgs()));
        return r;
    }

    private BasicDBObject getBasicDBObjectByMap(Map<String, String> map) {
        BasicDBObject r = new BasicDBObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            r.append(entry.getKey(), entry.getValue());
        }
        return r;
    }

    /**
     * 获取头信息
     *
     * @param request
     * @return
     */
    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

}
