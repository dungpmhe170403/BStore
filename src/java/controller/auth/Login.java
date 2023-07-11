package controller.auth;

import model.entity.User;
import service.UserService;
import ultis.Helper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {
    HashMap<String, Object> viewStatus;
    UserService userService;

    public void init() {
        // init dependency here
        viewStatus = new HashMap<String, Object>() {{
            put("isLogin", true);
            // if has some validtion use viewStatus to push more
        }};
        userService = new UserService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("data", viewStatus);
        request.getRequestDispatcher("views/auth.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!Helper.isEmptyParamsInRequest(request, new ArrayList<>(Arrays.asList("email", "password")))) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            User authenticatedUser = userService.authenticate(email, password);
            if (authenticatedUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedIn", true);
                session.setAttribute("user", authenticatedUser);
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }

    public void destroy() {
    }
}
