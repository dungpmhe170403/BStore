package controller.admin.products;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.entity.User;

import java.io.IOException;

public class AuthAdmin {

    public static boolean isAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        if (session != null) {
            User user = (User) session.getAttribute("user");
            return user != null && user.getRole() != null && user.getRole().equals("ADMIN");
        }
        return false;
    }
}
