package com.bupt.dc.config.config.auth.provider;

import com.bupt.dc.config.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Slf4j
public class TokenAuthenticateProvider implements AuthenticationProvider {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 客户端带token时的身份验证
     * 从token解析出用户名，查db获取用户信息
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        String username = jwtUtil.getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new PreAuthenticatedAuthenticationToken(username, null, userDetails.getAuthorities());
    }

    /**
     * authManager取authProviderList，调每个Provider的support方法，判断这个provider是否可以authenticate这个authentication
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.equals(authentication);
    }

}
