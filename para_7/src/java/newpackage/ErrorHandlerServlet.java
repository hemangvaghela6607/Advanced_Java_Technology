
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorHandlerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        showError(response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        showError(response);
    }

    private void showError(HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Error</title>");
        out.println("</head>");

        out.println("<body>");

        out.println("<h2 style='color:red;'>");
        out.println("Something went wrong!");
        out.println("</h2>");

        out.println("<p>");
        out.println("An error occurred during login.");
        out.println("</p>");

        out.println("<a href='index.html'>Back to Login</a>");

        out.println("</body>");
        out.println("</html>");
    }
}

