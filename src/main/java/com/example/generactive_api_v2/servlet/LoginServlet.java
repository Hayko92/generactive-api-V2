package com.example.generactive_api_v2.servlet;

import com.example.generactive_api_v2.servlet.dto.LoginRequestModel;
import com.example.generactive_api_v2.util.Credentials;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bf = req.getReader();
        while (bf.ready()) {
            stringBuilder.append(req.getReader().readLine());
        }
        String credentials = stringBuilder.toString();
        LoginRequestModel loginRequestModel = objectMapper.readValue(credentials, LoginRequestModel.class);
        if (Credentials.LOGIN.equals(loginRequestModel.getUsername())
                && Credentials.PASSWORD.equals(loginRequestModel.getPassword())) {
            HttpSession session = req.getSession();
            session.setAttribute("isLogined", true);
        }
    }
}
