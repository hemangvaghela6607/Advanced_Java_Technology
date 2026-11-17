package com.tuitionfee.filter;

import com.tuitionfee.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Security and Authorization Filter.
 * Protects admin and student routes, ensuring only authenticated users
 * with appropriate role permissions can access protected pages and servlets.
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/admin/*", "/student/*"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[AuthFilter] Initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String relativePath = uri.substring(contextPath.length());

        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

        // If not logged in, redirect to login page
        if (loggedInUser == null) {
            res.sendRedirect(contextPath + "/login.jsp?error=Please+login+to+continue.");
            return;
        }

        // Role-based authorization
        if (relativePath.startsWith("/admin")) {
            if (!loggedInUser.isAdmin()) {
                res.sendRedirect(contextPath + "/login.jsp?error=Unauthorized+access.+Admin+role+required.");
                return;
            }
        } else if (relativePath.startsWith("/student")) {
            if (!loggedInUser.isStudent()) {
                res.sendRedirect(contextPath + "/login.jsp?error=Unauthorized+access.+Student+role+required.");
                return;
            }
        }

        // User is authenticated and authorized; proceed with the chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("[AuthFilter] Destroyed.");
    }
}
