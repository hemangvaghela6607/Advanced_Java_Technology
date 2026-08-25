import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Welcome " + username + "</h2>");
        out.println("<h3>Login Successful!</h3>");
        out.println("</body></html>");
    }
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
