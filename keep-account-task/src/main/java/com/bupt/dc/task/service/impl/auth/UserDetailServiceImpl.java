package com.bupt.dc.task.service.impl.auth;

import com.bupt.dc.object.auth.User;
import com.bupt.dc.dao.jpa.UserRepository;
import com.bupt.dc.task.util.SecurityModelFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Cacheable(key = "#p0")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username is not valid.");
        }
        //log.info("The User is {}", user);
        return SecurityModelFactory.createUserDetails(user);
    }

}
