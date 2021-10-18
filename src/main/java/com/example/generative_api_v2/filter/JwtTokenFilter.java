package com.example.generative_api_v2.filter;

import com.example.generative_api_v2.configuration.userService.CustomUserDetailService;
import com.example.generative_api_v2.configuration.userService.MyUserPrincipal;
import com.example.generative_api_v2.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";

    private  JwtTokenUtil jwtProvider;
    private  CustomUserDetailService userDetailsService;

    public JwtTokenFilter() {
    }

    public JwtTokenUtil getJwtProvider() {
        return jwtProvider;
    }

    @Autowired
    public void setJwtProvider(JwtTokenUtil jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public CustomUserDetailService getUserDetailsService() {
        return userDetailsService;
    }
    @Autowired
    public void setUserDetailsService(CustomUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            MyUserPrincipal customUserDetails = userDetailsService.loadUserByUsername(userLogin);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}