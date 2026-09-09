/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package newpackage;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username or password cannot be empty");
        }

        if (username.equals("admin") &&
            password.equals("123")) {

            response.setContentType("text/html");

            response.getWriter().println(
                    "<h2>Login Successful</h2>");

        } else {

            throw new ServletException(
                    "Invalid username or password");
        }
    }
}