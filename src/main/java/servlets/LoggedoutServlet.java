package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that is responsible for the way our user logs out from our portal.
 * */
@WebServlet(("/loggedout"))
public class LoggedoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // destroys the session and all objects connected to it
        req.getSession().invalidate();
        // we change the global context of the userstate to anonymous,
        // so we don't let the system user to reach pages they are not supposed to reach
        getServletContext().setAttribute("userState", "anonymous");
        // we send the user to a specific logout page which gives them info about their logout
        req.getRequestDispatcher("/loggedout.jsp").forward(req,resp);
    }
}