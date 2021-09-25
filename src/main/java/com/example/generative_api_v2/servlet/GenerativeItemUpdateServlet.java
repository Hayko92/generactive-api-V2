package com.example.generative_api_v2.servlet;


import com.example.generative_api_v2.conf.ApplicationContext;
import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.service.GeneractiveItemServiceImpl;
import com.example.generative_api_v2.service.GenerativeItemService;
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

    private final GenerativeItemService generativeItemService
            = ApplicationContext.context.getBean("generactiveItemServiceImpl", GeneractiveItemServiceImpl.class);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        String[] parts = path.split("/");
        String idString = parts[parts.length - 1];
        ObjectMapper objectMapper = new ObjectMapper();

        String payLoad = req.getReader().lines().collect(Collectors.joining());
        GeneractiveDTO itemDTO = objectMapper.readValue(payLoad, GeneractiveDTO.class);
        try {
            int id = Integer.parseInt(idString);
            Generative finded = generativeItemService.getById(id);
            finded.setCurrency(itemDTO.getCurrency());
            finded.setImage_url(itemDTO.getImage_url());
            finded.setParent(itemDTO.getParent());
            finded.setPrice(itemDTO.getPrice());
            finded.setTitle(itemDTO.getTitle());
            finded.setComplexity(itemDTO.getComplexity());
            generativeItemService.updateById(finded);

            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(objectMapper.writeValueAsString(finded));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE, "WRONG ID");
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
            Generative item = generativeItemService.getById(id);
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(objectMapper.writeValueAsString(item));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();
        String[] parts = path.split("/");
        String idString = parts[parts.length - 1];
        try {
            int id = Integer.parseInt(idString);
            generativeItemService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
