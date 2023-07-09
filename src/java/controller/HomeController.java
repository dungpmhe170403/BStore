package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;
import service.ViewService;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "Home", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    HashMap<String, Object> viewData = new HashMap<>();
    ProductService productService;

    public void init() {
        ViewService viewService = ViewService.getInstance();
        viewData.put("brands", viewService.getAllBrands());
        viewData.put("view", "home.jsp");
        productService = new ProductService();
        viewData.put("latestProducts", productService.getLatestProduct());
        System.out.println(viewData.get("latestProducts"));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("data", viewData);
        request.getRequestDispatcher("layout.jsp").forward(request, response);
    }

}
