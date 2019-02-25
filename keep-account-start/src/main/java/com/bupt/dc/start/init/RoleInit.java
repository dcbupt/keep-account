package com.bupt.dc.start.init;

import com.bupt.dc.config.properties.SystemProperties;
import com.bupt.dc.dao.jpa.RoleRepository;
import com.bupt.dc.object.auth.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleInit {

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private RoleRepository roleRepository;

    public void init() {
        List<String> roleList = systemProperties.getUserRole();
        for (String role : roleList) {
            roleRepository.save(new Role(role));
        }
    }
}
