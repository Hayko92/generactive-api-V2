package com.example.generative_api_v2.servlet;


import com.example.generative_api_v2.conf.ApplicationContext;
import com.example.generative_api_v2.db.hibernate.GenerativeItemHibernateRepository;
import com.example.generative_api_v2.db.hibernate.StockItemHibernateRepository;
import com.example.generative_api_v2.db.jdbc.GenerativeItemJDBCRepository;
import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.service.GeneractiveItemServiceImpl;
import com.example.generative_api_v2.service.GenerativeItemService;
import com.example.generative_api_v2.service.StockItemServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "generactiveAddingItemServlet", value = "/generative-items")
public class GeneractiveItemServlet extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();
    private GeneractiveItemServiceImpl generativeItemService;

    @Override
    public void init() throws ServletException {
        super.init();
        generativeItemService = ApplicationContext.context.getBean("generactiveItemServiceImpl",GeneractiveItemServiceImpl.class);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String payload = req.getReader().lines().collect(Collectors.joining());
        Generative item;
        item = objectMapper.readValue(payload, Generative.class);
        generativeItemService.save(item);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(item));

    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        List<Generative> items = generativeItemService.getAll();
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(items));
    }
}
