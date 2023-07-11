package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.Cart;
import model.entity.Order;
import model.entity.User;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Or;
import service.CartService;
import service.OrderItemService;
import service.OrderService;
import service.ViewService;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "Check-out", urlPatterns = {"/check-out"})

public class CheckOutController extends HttpServlet {
    HashMap<String, Object> viewData = new HashMap<>();

    CartService cartService;
    OrderService orderService;

    public void init() {
        ViewService viewService = ViewService.getInstance();
        orderService = new OrderService();
        viewData.put("brands", viewService.getAllBrands());
        cartService = new CartService();
        viewData.put("view", "checkout.jsp");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user_id = (User) session.getAttribute("user");
        Cart cart = cartService.getCart((int) user_id.getUser_id());
        viewData.put("cart", cart);
        request.setAttribute("data", viewData);
        request.getRequestDispatcher("layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        int userId = (int) user.getUser_id();
        int isPay = 0;
        String phone = String.valueOf(req.getAttribute("phone"));
        String note = String.valueOf(req.getAttribute("note"));
        Order order = Order.builder()
                .phone(phone)
                .note(note)
                .isPay(0)
                .build();
        Order added = orderService.saveToCart(userId, order);
        if (added != null) {
            session.setAttribute("orderStatus", "Order Done");
        }else{
            session.setAttribute("orderStatus", "Order Fail");
        }
        resp.sendRedirect(req.getContextPath() + "/check-out");
    }
}
