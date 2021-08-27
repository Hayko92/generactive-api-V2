package com.example.generactive_api_v2.servlet;

import com.example.generactive_api_v2.db.Storage;
import com.example.generactive_api_v2.model.Group;
import com.example.generactive_api_v2.servlet.util.CheckCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "deleteGroupById", value = "/groups/*")
public class GroupDeleteServlet extends HttpServlet {
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!CheckCredentials.isLogined(req, resp)) return;
        String path = req.getPathInfo();
        String[] parts = path.split("/");
        String idString = parts[parts.length - 1];

        try {
            int id = Integer.parseInt(idString);
            Optional<Group> group = Storage.findGroupById(id);
            group.ifPresent(value -> Storage.getGroupList().remove(value));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE, "WRONG ID");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!CheckCredentials.isLogined(req, resp)) return;
        String path = req.getPathInfo();
        String[] parts = path.split("/");
        String idString = parts[parts.length - 1];
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader bf = req.getReader();
        StringBuilder body = new StringBuilder();
        while (bf.ready()) {
            body.append(bf.readLine());
        }
        Group group = objectMapper.readValue(body.toString(), Group.class);
        try {
            int id = Integer.parseInt(idString);
            Optional<Group> finded = Storage.findGroupById(id);
            finded.ifPresent(value -> Storage.getGroupList().remove(value));
            group.setId(id);
            Storage.addGroup(group);
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(objectMapper.writeValueAsString(group));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE, "WRONG ID");

        }
    }
}
