package com.example.generative_api_v2.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ErrorHandlingServlet", value = "/error")
public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer code = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String message = (String) req.getAttribute("javax.servlet.error.message");
        PrintWriter writer = resp.getWriter();
        String title = "Error Handling\n";
        writer.write(title);
        writer.write("Error code: " + code + "\n");
        writer.write("Error Message: " + message + "\n");
    }

}
