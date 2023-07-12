package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Product;
import model.helper.type.FilterProduct;
import service.ProductService;
import service.ViewService;
import ultis.DBHelper.bound.PaginationResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "Products", urlPatterns = {"/products"})
public class ProductController extends HttpServlet {
    HashMap<String, Object> viewData = new HashMap<>();
    ProductService productService;

    public void init() {
        ViewService viewService = ViewService.getInstance();
        viewData.put("brands", viewService.getAllBrands());
        viewData.put("view", "product-grid.jsp");
        productService = new ProductService();
        viewData.put("latestProducts", productService.getLatestProduct());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HashMap<FilterProduct, String> sortCondition = new HashMap<>();
        if (request.getParameter("page") == null) {
            response.sendRedirect("./products?page=1");
        } else {
            if (request.getParameter("price") != null) {
                sortCondition.put(FilterProduct.PRICE, request.getParameter("price"));
            }
            if (request.getParameter("brand") != null) {
                sortCondition.put(FilterProduct.BRAND, request.getParameter("brand"));
            }
            //tim kiem
            if (request.getParameter("q") != null) {
                sortCondition.put(FilterProduct.BRAND, request.getParameter("q"));
            }
            int page = Integer.parseInt(request.getParameter("page"));
            PaginationResponse<Product> data = productService.getProducts(page, "./products", sortCondition);
            viewData.put("products", data.responseData);
            viewData.put("links", data.links);
            System.out.println(data.links);
            viewData.put("currentPage", data.currentPage);
            request.setAttribute("data", viewData);
            request.getRequestDispatcher("layout.jsp").forward(request, response);
        }
    }
}
