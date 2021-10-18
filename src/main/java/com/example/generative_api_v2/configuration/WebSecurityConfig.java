package com.example.generative_api_v2.configuration;


import com.example.generative_api_v2.configuration.userService.CustomUserDetailService;
import com.example.generative_api_v2.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    DataSource dataSource;
    CustomUserDetailService userDetailService;
    JwtTokenFilter jwtFilter;

    public WebSecurityConfig() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CustomUserDetailService getUserDetailService() {
        return userDetailService;
    }

    @Autowired
    public void setUserDetailService(CustomUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    public JwtTokenFilter getJwtFilter() {
        return jwtFilter;
    }

    @Autowired
    public void setJwtFilter(JwtTokenFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", "/groups", "/items", "/items/*", "groups/*").hasAnyAuthority("CAN_READ_ALL_ITEMS", "CAN_READ_ALL_GROUPS")
                .antMatchers(HttpMethod.POST, "/", "/groups", "/items", "/items/*", "groups/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/", "/groups", "/items", "/items/*", "groups/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/", "/groups", "/items", "/items/*", "groups/*").hasRole("ADMIN")
                .antMatchers("/login", "/register").permitAll()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}