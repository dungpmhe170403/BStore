package controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.User;
import service.UserService;
import ultis.Helper;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "edit", urlPatterns = {"/edit-user"})
public class Edit extends HttpServlet {

    HashMap<String, Object> viewStatus;
    UserService userService;

    public void init() {
        // init dependency here
        viewStatus = new HashMap<String, Object>() {
            {
                // if has some validtion use viewStatus to push more
            }
        };
        userService = new UserService();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (!Helper.isEmptyParamsInRequest(request, true, "user_id", "password", "username", "address")) {
            User user = User.builder()
                    .user_id(Integer.parseInt(request.getParameter("user_id")))
                    .email(request.getParameter("email"))
                    .address(request.getParameter("address"))
                    .password(request.getParameter("password"))
                    .username(request.getParameter("username"))
                    .build();
            int isUpdated = this.userService.update(Integer.parseInt(request.getParameter("user_id")), user);
            if (isUpdated > 0) {
                User updated = userService.get(Integer.parseInt(request.getParameter("user_id")));
                session.setAttribute("user", updated);
                session.setAttribute("userEditResult", "Updated Success");
            } else {
                session.setAttribute("userEditResult", "Updated Failed");
            }
        } else {
            session.setAttribute("userEditResult", "Updated Failed");
        }
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
