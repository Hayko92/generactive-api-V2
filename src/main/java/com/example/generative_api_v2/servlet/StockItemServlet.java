package com.example.generative_api_v2.servlet;


import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "stockAddingItemServlet", value = "/items")
public class StockItemServlet extends HttpServlet {
    private final ItemService itemService;

    @Autowired
    public StockItemServlet(ItemService itemService) {
        this.itemService = itemService;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String payload = req.getReader().lines().collect(Collectors.joining());
        Item item;
        item = objectMapper.readValue(payload, Item.class);
        itemService.save(item);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(item));

    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        List<Item> items = itemService.getAll();
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(items));
    }
}
