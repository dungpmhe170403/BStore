package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.OrderItem;
import model.entity.Product;
import model.entity.User;
import service.CartService;
import service.ProductService;
import service.ViewService;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "ProductDetail", urlPatterns = {"/product-detail"})
public class ProductDetailsController extends HttpServlet {
    HashMap<String, Object> viewData = new HashMap<>();
    ProductService productService;

    public void init() {
        ViewService viewService = ViewService.getInstance();
        viewData.put("brands", viewService.getAllBrands());
        viewData.put("view", "shop-details.jsp");
        productService = new ProductService();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            String childPath = req.getParameter("id");
            try {
                int id = Integer.parseInt(childPath);
                Product product = productService.getProduct(id);
                if (product != null) {
                    viewData.put("product", product);
                } else {
                    viewData.replace("view", "notfound.jsp");
                }
            } catch (Exception e) {
                e.printStackTrace();
                viewData.replace("view", "notfound.jsp");
            }
        } else {
            viewData.replace("view", "notfound.jsp");
        }
        req.setAttribute("data", viewData);
        req.getRequestDispatcher("layout.jsp").forward(req, resp);
    }


}