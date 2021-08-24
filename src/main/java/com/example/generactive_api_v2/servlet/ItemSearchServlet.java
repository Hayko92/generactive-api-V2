package com.example.generactive_api_v2.servlet;


import com.example.generactive_api_v2.db.Storage;
import com.example.generactive_api_v2.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String fromStr = req.getParameter("priceFrom");
        String toSTr = req.getParameter("priceTo");
        int from = Integer.parseInt(fromStr);
        int to = Integer.parseInt(toSTr);
        List<Item> result = Storage.getItemsWithPriceFromTo(from, to);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(result));

    }
}
