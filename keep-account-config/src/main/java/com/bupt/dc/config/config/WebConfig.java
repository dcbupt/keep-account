package com.bupt.dc.config.config;

import com.bupt.dc.config.config.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 日志拦截器
     */
    @Autowired
    private LogInterceptor logInterceptor;

    /**
     * 重写添加拦截器方法并添加配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
    }

    //@Override
    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //    registry.addResourceHandler("swagger-ui.html")
    //        .addResourceLocations("classpath:/META-INF/resources/");
    //
    //    registry.addResourceHandler("/webjars/**")
    //        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    //}
}
