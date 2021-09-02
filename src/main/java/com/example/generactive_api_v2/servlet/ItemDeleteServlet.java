package com.example.generactive_api_v2.servlet;


import com.example.generactive_api_v2.db.Storage;
import com.example.generactive_api_v2.model.Item;
import com.example.generactive_api_v2.dto.ItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.stream.Collectors;

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

        String payLoad = req.getReader().lines().collect(Collectors.joining());
        ItemDTO itemDTO = objectMapper.readValue(payLoad, ItemDTO.class);
        try {
            int id = Integer.parseInt(idString);
            Optional<Item> finded = Storage.findItemById(id);
            if (finded.isPresent()) {
                Item item = finded.get();
                item.setConfiguration(itemDTO.getConfiguration());
                item.setCurrency(itemDTO.getCurrency());
                item.setParent(itemDTO.getParent());
                item.setPrice(itemDTO.getPrice());
                item.setTitle(itemDTO.getTitle());
                item.setImage_url(itemDTO.getImage_url());

            } else return;

            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(objectMapper.writeValueAsString(finded.get()));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE, "WRONG ID");

        }
    }
}
