package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.Cart;
import model.entity.OrderItem;
import model.entity.User;
import service.CartService;
import service.OrderItemService;
import service.ViewService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@WebServlet(name = "Cart", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    HashMap<String, Object> viewData = new HashMap<>();

    CartService cartService;
    OrderItemService orderItemService;

    public void init() {
        ViewService viewService = ViewService.getInstance();
        viewData.put("brands", viewService.getAllBrands());
        cartService = new CartService();
        orderItemService = new OrderItemService();
        viewData.put("view", "cart.jsp");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user_id = (User) session.getAttribute("user");
        Cart cart = cartService.getCart((int) user_id.getUser_id());
        viewData.put("cart", cart);
        session.setAttribute("cart", cart);
        request.setAttribute("data", viewData);
        request.getRequestDispatcher("layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("updateCart") != null && String.valueOf(req.getParameter("updateCart")).equals("updateCart")) {
            ArrayList<String> shoes_ids = new ArrayList<>(Arrays.asList(req.getParameterValues("shoes_id")));
            ArrayList<String> quantity = new ArrayList<>(Arrays.asList(req.getParameterValues("quantity")));
            ArrayList<String> size = new ArrayList<>(Arrays.asList(req.getParameterValues("size")));
            Cart cart = (Cart) session.getAttribute("cart");
            System.out.println(cart);
            int count = 0;
            for (OrderItem od : cart.getItems()) {
                if (od.getProduct().getId().equals(Integer.parseInt(shoes_ids.get(count)))) {
                    od.setQuantity(Integer.parseInt(quantity.get(count)));
                    od.setSize(size.get(count));
                    od.setCart_id(cart.getCart_id());
                    od.setProduct_id(od.getProduct().getId());
                    orderItemService.update(od, "order_item_id =" + od.getOrder_item_id());
                }
                count++;
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
        } else {
            AddToCart(req, resp);
        }
    }

    public void AddToCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        boolean isAdded = false;
        if (req.getParameter("shoes_id") != null) {
            try {
                int shoes_id = Integer.parseInt(req.getParameter("shoes_id"));

                User user = (User) session.getAttribute("user");
                OrderItem data = OrderItem.builder()
                        .product_id(shoes_id)
                        .build();
                OrderItem added = cartService.saveToCart((int) user.getUser_id(), data);
                isAdded = true;
            } catch (Exception e) {

            }
        }
        session.setAttribute("addToCart", isAdded ? "Added" : "Failed");
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isDeleted = false;
        String id = req.getParameter("id");
        System.out.println(id);
        if (id != null) {
            int order_id = Integer.parseInt(id);
            int result = this.orderItemService.delete(order_id);
            if (result > 0) {
                isDeleted = true;
            }
        }
        resp.setStatus(isDeleted ? 200 : 500);
        resp.getWriter().println(isDeleted);
    }
}
