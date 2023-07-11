package controller.admin.products;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.entity.Product;
import service.ProductService;
import service.ViewService;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static controller.admin.products.AuthAdmin.isAdmin;
import ultis.Helper;

@WebServlet(name = "admin-add-product", urlPatterns = {"/admin-add-products"})
@MultipartConfig
public class AddProductController extends HttpServlet {
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
        viewData.put("form-action", "./admin-add-products");
        request.setAttribute("data", viewData);
        request.getRequestDispatcher("views/admin/layout/admin-layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<String> files = uploadFile(req);
        String name = req.getParameter("name");
        Float price = Float.parseFloat(req.getParameter("price"));
        String description = req.getParameter("description");
        Integer brand = Integer.parseInt(req.getParameter("brand"));
        Product newProduct = Product.builder()
                .brand(brand)
                .price(price)
                .description(description)
                .name(name)
                .build();
        Product saved = productService.saveProduct(newProduct, files);
        if (saved == null) {
            resp.sendRedirect("./admin-add-products");
        } else {
            resp.sendRedirect("./admin-products");
        }
    }

    private ArrayList<String> uploadFile(HttpServletRequest request) throws ServletException, IOException {
        List<Part> fileParts = request.getParts().stream()
                .filter(part -> part.getName().startsWith("images"))
                .collect(Collectors.toList());

        ArrayList<String> fileNames = new ArrayList<>();
        String savePath = Helper.getValueFromAppProperties("image.storage");
        System.out.println(savePath);
        for (Part filePart : fileParts) {
            String fileName = UUID.randomUUID() + ".jpg";
            fileNames.add(fileName);
            String filePath = savePath + File.separator + fileName;
            try {
                FileOutputStream fos = new FileOutputStream(filePath);
                InputStream is = filePart.getInputStream();
                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
                fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return fileNames;
    }
}
