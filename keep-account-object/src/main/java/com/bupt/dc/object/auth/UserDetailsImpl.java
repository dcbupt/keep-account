package com.bupt.dc.object.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    /**
     * 获取权限信息
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> userRoles = user.getRoles();
        List<GrantedAuthority> auths = new ArrayList<>(userRoles.size());
        userRoles.parallelStream().forEach(userRole -> {
            // 默认ROLE_  为前缀，可以更改
            auths.add(new SimpleGrantedAuthority("ROLE_" + userRole.getName()));
        });
        return auths;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否激活
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
