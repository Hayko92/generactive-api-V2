package com.example.generactive_api_v2.servlet;


import com.example.generactive_api_v2.db.Storage;
import com.example.generactive_api_v2.model.Item;
import com.example.generactive_api_v2.servlet.util.CheckCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "addingItemServlet", value = "/items")
public class ItemServlet extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader bf = req.getReader();
        StringBuilder body = new StringBuilder();
        while (bf.ready()) {
            body.append(bf.readLine());
        }
        Item item = objectMapper.readValue(body.toString(), Item.class);
        Storage.addItem(item);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(item));
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        List<Item> items = Storage.getItemList();
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(items));
    }
}
