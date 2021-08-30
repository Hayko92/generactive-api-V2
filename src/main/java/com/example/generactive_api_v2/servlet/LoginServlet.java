package com.example.generactive_api_v2.servlet;

import com.example.generactive_api_v2.servlet.dto.LoginRequestModel;
import com.example.generactive_api_v2.util.Credentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String payLoad= req.getReader().lines().collect(Collectors.joining());
        LoginRequestModel loginRequestModel = objectMapper.readValue(payLoad, LoginRequestModel.class);
        if (Credentials.LOGIN.equals(loginRequestModel.getUsername())
                && Credentials.PASSWORD.equals(loginRequestModel.getPassword())) {
            HttpSession session = req.getSession();
            session.setAttribute("isLogined", true);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("you have send wrong credentials");
        }
    }
}
