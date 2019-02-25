package com.bupt.dc.config.config;

import com.bupt.dc.config.config.auth.AuthenticationFilter;
import com.bupt.dc.config.config.auth.provider.LoginAuthenticationProvider;
import com.bupt.dc.config.config.auth.provider.TokenAuthenticateProvider;
import com.bupt.dc.config.utils.JwtUtil;
import com.bupt.dc.task.service.impl.auth.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // 开启 Security
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public UserDetailsService customService() {
        return new UserDetailServiceImpl();
    }

    @Bean("loginAuthenticationProvider")
    public AuthenticationProvider loginAuthenticationProvider() {
        return new LoginAuthenticationProvider();
    }

    @Bean("tokenAuthenticationProvider")
    public AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticateProvider();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }
    /**
     * 主要是对身份验证的设置
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(loginAuthenticationProvider())
            .authenticationProvider(tokenAuthenticationProvider())
            .userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            // 设置 session 状态 STATELESS 无状态
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            // testController不需要权限认证
            //.antMatchers("/test/**").permitAll()
            .antMatchers("/user/**").authenticated()
            .antMatchers("/**").permitAll()
            //.anyRequest().authenticated()
            .and()
            .formLogin()
            //.successHandler(new ForwardAuthenticationSuccessHandler("/home"))
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/home")
            //.defaultSuccessUrl("/process.html")
            //.failureUrl("/logout.html")
            .and()
            .addFilter(authenticationFilter())
        ;

    }

}
