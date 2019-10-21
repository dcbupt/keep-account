package com.bupt.dc.config.config.auth;

import com.bupt.dc.config.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
        ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        log.info("AuthenticationFilter|doFilter|enter|url:{}", ((HttpServletRequest)request).getRequestURL());
        try {
            if (isLoginRequest(httpRequest, httpResponse)) {
                Authentication authResult = processLogin(httpRequest, httpResponse);
                successfulAuthentication(httpRequest, httpResponse, chain, authResult);
                return;
            }
            String token = obtainToken(httpRequest);
            if (StringUtils.isNotBlank(token)) {
                processTokenAuthentication(token);
            }
        } catch (AuthenticationException e) {
            unsuccessfulAuthentication(httpRequest, httpResponse, e);
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * 登陆成功调用，返回 token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        String token = jwtUtil.generateToken(authResult.getName());
        response.setStatus(HttpStatus.OK.value());
        Cookie cookie = new Cookie(jwtUtil.getHeader(), token);
        /** cookie有效期，单位秒 **/
        cookie.setMaxAge(300);
        response.addCookie(cookie);
        response.sendRedirect("/home/");
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    private boolean isLoginRequest(HttpServletRequest request, HttpServletResponse response) {
        return requiresAuthentication(request, response) && "POST".equalsIgnoreCase(request.getMethod());
    }

    private String obtainToken(HttpServletRequest request) {
        //return request.getHeader(jwtUtil.getHeader());
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return StringUtils.EMPTY;
        }
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), jwtUtil.getHeader())) {
                return cookie.getValue();
            }
        }
        return StringUtils.EMPTY;
    }

    private Authentication processLogin(HttpServletRequest request, HttpServletResponse response) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        return tryAuthenticationWithUsernameAndPassword(username, password);
    }

    private void processTokenAuthentication(String token) {
        Authentication resultOfAuthentication = tryToAuthenticateWithToken(token);
        // 设置上下文用户信息以及权限
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
    }

    private Authentication tryAuthenticationWithUsernameAndPassword(String username, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        return tryToAuthenticate(authentication);
    }

    private Authentication tryToAuthenticateWithToken(String token) {
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token, null);
        return tryToAuthenticate(requestAuthentication);
    }

    private Authentication tryToAuthenticate(Authentication requestAuth) {
        Authentication responseAuth = getAuthenticationManager().authenticate(requestAuth);
        if (responseAuth == null || !responseAuth.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }
        log.info("User successfully authenticated");
        return responseAuth;
    }

}
