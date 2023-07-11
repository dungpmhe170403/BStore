package controller.admin.products;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.entity.Product;
import service.ProductService;
import service.ViewService;

import java.io.IOException;
import java.util.HashMap;

import static controller.admin.products.AuthAdmin.isAdmin;

@MultipartConfig
@WebServlet(name = "admin-edit-product", urlPatterns = {"/admin-edit-products"})
public class EditProductController extends HttpServlet {
    HashMap<String, Object> viewData = new HashMap<>();
    ProductService productService;

    public void init() {
        ViewService viewService = ViewService.getInstance();
        viewData.put("brands", viewService.getAllBrands());
        viewData.put("view", "product-form.jsp");
        productService = new ProductService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!isAdmin(request, response)) {
            response.sendRedirect("./login");
            return;
        }
        int id = Integer.parseInt(request.getParameter("shoes_id"));
        Product product = productService.getProduct(id);
        if (product != null) {
            viewData.put("product", product);
        } else {
            viewData.replace("view", "notfound.jsp");
        }
        viewData.put("form-action", "./admin-edit-products");
        HttpSession session = request.getSession();
        session.setAttribute("edit-shoes", id);
        viewData.put("isEdit", true);
        request.setAttribute("data", viewData);
        request.getRequestDispatcher("views/admin/layout/admin-layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int id = (int) session.getAttribute("edit-shoes");
        String name = req.getParameter("name");
        Float price = Float.parseFloat(req.getParameter("price"));
        String description = req.getParameter("description");
        Integer brand = Integer.parseInt(req.getParameter("brand"));
        Product newProduct = Product.builder()
                .brand(brand)
                .price(price)
                .name(name)
                .build();
        if (!description.isEmpty()) {
            newProduct.setDescription(description);
        }
        int saved = productService.updateProduct(newProduct, id);
        session.removeAttribute("edit-shoes");
        resp.sendRedirect("./admin-products");
    }
}
