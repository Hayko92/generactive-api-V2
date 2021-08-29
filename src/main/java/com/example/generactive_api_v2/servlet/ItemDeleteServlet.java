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
import java.util.Optional;

@WebServlet(name = "deleteItemById", value = "/items/*")
public class ItemDeleteServlet extends HttpServlet {
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();
        String[] parts = path.split("/");
        String idString = parts[parts.length - 1];

        try {
            int id = Integer.parseInt(idString);
            Optional<Item> item = Storage.findItemById(id);
            item.ifPresent(value -> Storage.getItemList().remove(value));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE, "WRONG ID");

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        String[] parts = path.split("/");
        String idString = parts[parts.length - 1];
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader bf = req.getReader();
        StringBuilder body = new StringBuilder();
        while (bf.ready()) {
            body.append(bf.readLine());
        }
        Item item = objectMapper.readValue(body.toString(), Item.class);
        try {
            int id = Integer.parseInt(idString);
            Optional<Item> finded = Storage.findItemById(id);
            finded.ifPresent(value -> Storage.getItemList().remove(value));
            item.setId(id);
            Storage.addItem(item);
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(objectMapper.writeValueAsString(item));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE, "WRONG ID");

        }
    }
}
