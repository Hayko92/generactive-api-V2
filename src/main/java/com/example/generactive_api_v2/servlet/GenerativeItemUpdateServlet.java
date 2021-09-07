package com.example.generactive_api_v2.servlet;


import com.example.generactive_api_v2.db.GenerativeItemJDBCRepository;
import com.example.generactive_api_v2.dto.GeneractiveDTO;
import com.example.generactive_api_v2.model.Generative;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "updateGenerativeItemById", value = "/generative-items/*")
public class GenerativeItemUpdateServlet extends HttpServlet {


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        String[] parts = path.split("/");
        String idString = parts[parts.length - 1];
        ObjectMapper objectMapper = new ObjectMapper();

        String payLoad = req.getReader().lines().collect(Collectors.joining());
        GeneractiveDTO itemDTO=objectMapper.readValue(payLoad, GeneractiveDTO.class);
        try {
            int id = Integer.parseInt(idString);
            Generative finded = GenerativeItemJDBCRepository.findItemById(id);
            if (finded != null) {
                finded.setConfiguration(itemDTO.getConfiguration());
                finded.setCurrency(itemDTO.getCurrency());
                finded.setParent(itemDTO.getParent());
                finded.setPrice(itemDTO.getPrice());
                finded.setTitle(itemDTO.getTitle());
                finded.setImage_url(itemDTO.getImage_url());
                finded.setComplexity(itemDTO.getComplexity());
                GenerativeItemJDBCRepository.updateById(id, itemDTO);
            } else return;

            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(objectMapper.writeValueAsString(finded));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE, "WRONG ID");
        }
    }
}
