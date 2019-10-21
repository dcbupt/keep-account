//package com.bupt.dc.config.config.response;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Configuration
//@Slf4j
//public class CrossFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
//
//        ServletException, IOException {
//        log.info("CrossFilter|requestMethod:{}", request.getMethod());
//
//        // CORS "pre-flight" request
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
//        response.addHeader("Access-Control-Allow-Headers", "'Content-Type");
//        //response.addHeader("Access-Control-Allow-Headers", "'Content-Type, Content-Length, Authorization, Accept, X-Requested-With , yourHeaderFeild");
//        response.addHeader("Access-Control-Max-Age", "1800");//30 min
//
//        filterChain.doFilter(request, response);
//    }
//
//}
