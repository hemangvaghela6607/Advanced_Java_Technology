import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.equals("Hemang") && password.equals("123")) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            RequestDispatcher rd = request.getRequestDispatcher("ProfileServlet");
            rd.forward(request, response);
        } 
        else {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<body>");
            out.println("<h3 style='color:red;'>Invalid Username or Password!</h3>");
            out.println("<a href='index.html'>Back to Login</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("index.html");
    }
}

