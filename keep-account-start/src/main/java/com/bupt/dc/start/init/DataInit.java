package com.bupt.dc.start.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class DataInit {

    @Bean(initMethod = "init")
    @Order(1)
    public RoleInit roleInit() {
        return new RoleInit();
    }

    @Bean(initMethod = "init")
    @Order(2)
    public UserInit userInit() {
        return new UserInit();
    }

}
