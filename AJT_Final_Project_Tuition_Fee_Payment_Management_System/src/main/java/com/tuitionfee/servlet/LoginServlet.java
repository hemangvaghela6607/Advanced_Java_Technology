package com.tuitionfee.servlet;

import com.tuitionfee.model.User;
import com.tuitionfee.service.UserService;
import com.tuitionfee.util.SpringContextHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Controller handling user authentication (Login).
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        // Retrieve Spring-managed UserService singleton
        this.userService = SpringContextHelper.getBean("userService", UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // If already logged in, redirect to respective dashboard
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            User user = (User) session.getAttribute("loggedInUser");
            if (user.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                return;
            } else if (user.isStudent()) {
                resp.sendRedirect(req.getContextPath() + "/student/dashboard");
                return;
            }
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Server-side validation
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            req.setAttribute("errorMessage", "Email and Password are required.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        User user = userService.authenticate(email, password);
        if (user != null) {
            // Create session and store user object
            HttpSession session = req.getSession(true);
            session.setAttribute("loggedInUser", user);

            if (user.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/student/dashboard");
            }
        } else {
            req.setAttribute("errorMessage", "Invalid email or password. Please try again.");
            req.setAttribute("enteredEmail", email);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
