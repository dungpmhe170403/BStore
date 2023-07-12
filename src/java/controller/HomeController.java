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
    // view data de gop cac du lieu se dc tra ve view 
    HashMap<String, Object> viewData = new HashMap<>();
    // can su dung service trong controller de lay du lieu
    ProductService productService;
// ham init dc chay moi khi controller dc truy cap]
    // do do cac  du lieu cung nhu brand thi se dc truc tiep goi ra o giai doan nay
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
