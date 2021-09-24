package com.example.generative_api_v2.servlet;


import com.example.generative_api_v2.conf.ApplicationContext;
import com.example.generative_api_v2.db.hibernate.StockItemHibernateRepository;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.service.ItemService;
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

@WebServlet(name = "searchItemsServlet", value = "/items/search")
public class ItemSearchServlet extends HttpServlet {

    private StockItemServiceImpl itemService;

    @Override
    public void init() throws ServletException {
        super.init();
        itemService = ApplicationContext.context.getBean("stockItemServiceImpl",StockItemServiceImpl.class);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String fromStr = req.getParameter("priceFrom");
        String toSTr = req.getParameter("priceTo");
        int from = Integer.parseInt(fromStr);
        int to = Integer.parseInt(toSTr);
        List<Item> result = itemService.getItemsWithPriceFromTo(from, to);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(result));

    }
}
