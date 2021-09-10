package com.example.generative_api_v2.filter;

import com.example.generative_api_v2.servlet.util.CheckCredentials;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/items/*", "/groups/*","/groups","/items"})
public class LoginFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        if (!CheckCredentials.isLogined(req)) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = res.getWriter();
            writer.write("sorry,you have not permissions to this page");
        } else chain.doFilter(req, res);
    }
}
