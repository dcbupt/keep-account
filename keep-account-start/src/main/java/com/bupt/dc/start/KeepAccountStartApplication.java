package com.bupt.dc.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class})
@ComponentScan(basePackages = {"com.bupt.dc"})
@MapperScan("com.bupt.dc.dao.ibatis.service")
@EnableJpaRepositories(basePackages = "com.bupt.dc.dao.jpa")
@EntityScan(basePackages = "com.bupt.dc")
@EnableScheduling
@EnableAsync
public class KeepAccountStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeepAccountStartApplication.class, args);
    }

}

