package com.bupt.dc.task.util;

import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.auth.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityModelFactory {

    public static UserDetails createUserDetails(User user) {
        return new UserDetailsImpl(user);
    }

}
