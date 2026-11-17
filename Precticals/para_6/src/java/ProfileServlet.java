import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Profile</title>");
        out.println("</head>");
        out.println("<body>");

        if (session != null) {

            String username = (String) session.getAttribute("username");
            if (username != null) {
                out.println("<h2>Welcome " + username + "</h2>");
                out.println("<h3>Login Successful</h3>");
            } else {
                out.println("<h3>Please login first.</h3>");
                out.println("<a href='index.html'>Login</a>");
            }
        } else {
            out.println("<h3>Session Expired. Please login again.</h3>");
            out.println("<a href='index.html'>Login</a>");
        }
        out.println("</body>");
        out.println("</html>");
    }
}