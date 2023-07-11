    package controller.admin.products;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.Product;
import model.helper.type.FilterProduct;
import service.ProductService;
import service.ViewService;
import ultis.DBHelper.bound.PaginationResponse;

import java.io.IOException;
import java.util.HashMap;

import static controller.admin.products.AuthAdmin.isAdmin;

@WebServlet(name = "adminProduct", urlPatterns = {"/admin-products"})
public class AdminProductController extends HttpServlet {
    HashMap<String, Object> viewData = new HashMap<>();
    ProductService productService;

    public void init() {
        viewData.put("view", "products.jsp");
        productService = new ProductService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!isAdmin(request, response)) {
            response.sendRedirect("./login");
            return;
        }
        HttpSession session = request.getSession();
        HashMap<FilterProduct, String> sortCondition = new HashMap<>();
        if (request.getParameter("page") == null) {
            response.sendRedirect("./admin-products?page=1");
            return;
        }
        if (request.getParameter("q") != null) {
            session.setAttribute("q", request.getParameter("q"));
            sortCondition.put(FilterProduct.KEY, request.getParameter("q"));
        }
        int page = Integer.parseInt(request.getParameter("page"));
        PaginationResponse<Product> data = productService.getProducts(page, "./admin-products", sortCondition);
        viewData.put("products", data.responseData);
        viewData.put("links", data.links);
        viewData.put("currentPage", data.currentPage);
        request.setAttribute("data", viewData);
        request.getRequestDispatcher("views/admin/layout/admin-layout.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        boolean isDeleted = false;
        if (id != null) {
            int del = Integer.parseInt(id);
            int result = this.productService.delete(del);
            if (result > 0) {
                isDeleted = true;
            }
        }
        resp.setStatus(isDeleted ? 200 : 500);
        resp.getWriter().println(isDeleted);
    }
}
