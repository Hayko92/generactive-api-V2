package com.example.generative_api_v2.servlet;


import com.example.generative_api_v2.db.hibernate.StockItemHibernateRepository;
import com.example.generative_api_v2.db.jdbc.StockItemJDBCRepository;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.model.Stock;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String payload = req.getReader().lines().collect(Collectors.joining());
        Stock item;
        item = objectMapper.readValue(payload, Stock.class);
        StockItemHibernateRepository.save(item);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(item));

    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        List<Item> items = StockItemJDBCRepository.getAll();
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(items));
    }
}
