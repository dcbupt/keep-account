package com.bupt.dc.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class SystemProperties {

    @Value("${project.name}")
    public String projectName;

    @Value("${author.name}")
    public String authorName;

    @Value("#{'${user.role}'.split(',')}")
    public List<String> userRole;

    @Value("${all.authority.username}")
    public String username;

    @Value("${all.authority.password}")
    public String password;

    public static final String prop = "test";

}
