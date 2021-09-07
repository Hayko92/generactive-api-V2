package com.example.generactive_api_v2.servlet;

import com.example.generactive_api_v2.db.GroupJDBCReposotory;
import com.example.generactive_api_v2.db.Storage;
import com.example.generactive_api_v2.model.Group;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "groupServlet" , value = "/groups")
public class GroupServlet extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        List<Group> groups = GroupJDBCReposotory.getGroupList();
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(groups));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader bf = req.getReader();
        StringBuilder body = new StringBuilder();
        while (bf.ready()) {
            body.append(bf.readLine());
        }
        Group group = objectMapper.readValue(body.toString(), Group.class);
        Storage.addGroup(group);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(group));
    }
}
