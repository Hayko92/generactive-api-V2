package com.example.generactive_api_v2.servlet;


import com.example.generactive_api_v2.db.ItemJDBCRepository;
import com.example.generactive_api_v2.model.Generative;
import com.example.generactive_api_v2.model.Item;
import com.example.generactive_api_v2.model.Stock;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "addingItemServlet", value = "/items")
public class ItemServlet extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String payload = req.getReader().lines().collect(Collectors.joining());
        Item item;
        if (payload.contains("complexity")) {
            item = objectMapper.readValue(payload, Generative.class);
        } else item = objectMapper.readValue(payload, Stock.class);
        ItemJDBCRepository.add(item);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(item));

    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        List<Item> items = ItemJDBCRepository.getAll();
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(items));
    }
}
