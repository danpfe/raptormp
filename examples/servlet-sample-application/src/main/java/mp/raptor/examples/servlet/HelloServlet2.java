package mp.raptor.examples.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 */
@WebServlet(
        name = "HelloServlet2",
        description = "This is a description of a servlet",
        urlPatterns = "/another/hello"
)
public class HelloServlet2 extends HttpServlet {

    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.getWriter().println("Hello");
    }

}
