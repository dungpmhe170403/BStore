package controller;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "Home", urlPatterns = {"/home"})
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
       
        // init dependency here
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("view", "home.jsp");
        request.getRequestDispatcher("layout.jsp").forward(request, response);
    }

    public void destroy() {
    }
}