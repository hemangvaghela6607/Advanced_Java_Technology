import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ev1_94","root","");
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Create session
                HttpSession session = request.getSession();
                // Store username in session
                session.setAttribute("username", username);
                // Redirect to ProfileServlet
                response.sendRedirect(
                        request.getContextPath() + "/ProfileServlet"
                );
            } else {
                out.println("<h3 style='color:red;'>Invalid Username or Password</h3>");
                out.println("<a href='index.html'>Try Again</a>");
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            out.println("<h3>Database Error: " + e.getMessage() + "</h3>");
        }
    }
}