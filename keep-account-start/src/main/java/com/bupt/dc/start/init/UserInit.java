package com.bupt.dc.start.init;

import com.bupt.dc.object.constant.RoleEnum;
import com.bupt.dc.config.properties.SystemProperties;
import com.bupt.dc.dao.jpa.RoleRepository;
import com.bupt.dc.object.auth.Role;
import com.bupt.dc.object.auth.User;
import com.bupt.dc.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Set;

public class UserInit {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SystemProperties systemProperties;

    public void init() {
        User admin = new User(systemProperties.getUsername(), systemProperties.getPassword(), new Date(System.currentTimeMillis()));
        Set<Role> roles = roleRepository.findByNameIn(RoleEnum.getRoles());
        admin.setRoles(roles);
        userService.insertUser(admin);
    }

}
