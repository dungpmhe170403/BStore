package controller.auth;

import model.entity.User;
import service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "Signup", urlPatterns = {"/signup"})
public class SignUp extends HttpServlet {
    HashMap<String, Object> viewStatus;
    UserService userService;

    public void init() {
        // init dependency here
        viewStatus = new HashMap<String,Object>() {{
            put("isLogin", false);
            // if has some validtion use viewStatus to push more
        }};
        userService = new UserService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setAttribute("data", viewStatus);
        request.getRequestDispatcher("views/auth.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("email") != null && !userService.checkEmailIsUnique(req.getParameter("email")) &&
                req.getParameter("password").equals(req.getParameter("password_confirmation"))
        ) {
            User registerUser = User.builder()
                    .email(req.getParameter("email"))
                    .password(req.getParameter("password"))
                    .username(req.getParameter("username"))
                    .build();
            if (userService.saveUser(registerUser) != null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                System.out.println("created" + registerUser);
                return;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/signup");
    }

    public void destroy() {
    }
}
