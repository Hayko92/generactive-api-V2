package com.example.generative_api_v2.servlet;


import com.example.generative_api_v2.db.hibernate.StockItemHibernateRepository;
import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "deleteItemById", value = "/items/*")
public class StockItemDeleteServlet extends HttpServlet {
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();
        String[] parts = path.split("/");
        String idString = parts[parts.length - 1];
        try {
            int id = Integer.parseInt(idString);
            StockItemHibernateRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ObjectMapper objectMapper = new ObjectMapper();
        String path = req.getPathInfo();
        String[] parts = path.split("/");
        String idString = parts[parts.length - 1];
        try {
            int id = Integer.parseInt(idString);
            Item item = StockItemHibernateRepository.getById(id);
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(objectMapper.writeValueAsString(item));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        String[] parts = path.split("/");
        String idString = parts[parts.length - 1];
        ObjectMapper objectMapper = new ObjectMapper();

        String payLoad = req.getReader().lines().collect(Collectors.joining());
        ItemDTO itemDTO = objectMapper.readValue(payLoad, ItemDTO.class);
        try {
            int id = Integer.parseInt(idString);
            Item finded = StockItemHibernateRepository.getById(id);
            finded.setConfiguration(itemDTO.getConfiguration());
            finded.setCurrency(itemDTO.getCurrency());
            finded.setImage_url(itemDTO.getImage_url());
            finded.setParent(itemDTO.getParent());
            finded.setPrice(itemDTO.getPrice());
            finded.setTitle(itemDTO.getTitle());
            StockItemHibernateRepository.updateById(finded);

            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(objectMapper.writeValueAsString(finded));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE, "WRONG ID");
        }
    }
}
