package com.example.generactive_api_v2.servlet;


import com.example.generactive_api_v2.db.StockItemJDBCRepository;
import com.example.generactive_api_v2.dto.GeneractiveDTO;
import com.example.generactive_api_v2.dto.ItemDTO;
import com.example.generactive_api_v2.model.Generative;
import com.example.generactive_api_v2.model.Item;
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
            StockItemJDBCRepository.deleteById(id);
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
        ItemDTO itemDTO=objectMapper.readValue(payLoad, ItemDTO.class);
        try {
            int id = Integer.parseInt(idString);
            Item finded = StockItemJDBCRepository.findItemById(id);
            if (finded != null) {
                finded.setConfiguration(itemDTO.getConfiguration());
                finded.setCurrency(itemDTO.getCurrency());
                finded.setParent(itemDTO.getParent());
                finded.setPrice(itemDTO.getPrice());
                finded.setTitle(itemDTO.getTitle());
                finded.setImage_url(itemDTO.getImage_url());
                StockItemJDBCRepository.updateById(id, itemDTO);
            } else return;

            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(objectMapper.writeValueAsString(finded));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE, "WRONG ID");
        }
    }
}
