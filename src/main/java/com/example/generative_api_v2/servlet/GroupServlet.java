package com.example.generative_api_v2.servlet;


import com.example.generative_api_v2.conf.ApplicationContext;
import com.example.generative_api_v2.model.Group;
import com.example.generative_api_v2.service.GroupService;
import com.example.generative_api_v2.service.GroupServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "groupServlet", value = "/groups")
public class GroupServlet extends HttpServlet {

    ObjectMapper objectMapper = new ObjectMapper();
    GroupService groupService = ApplicationContext.context.getBean("groupServiceImpl", GroupServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        List<Group> groups = groupService.getAll();
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(groups));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String payload = req.getReader().lines().collect(Collectors.joining());
        Group group = objectMapper.readValue(payload, Group.class);
        groupService.save(group);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(group));
    }

}
