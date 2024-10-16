package servlets;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;

@WebListener
public class SessionHandler extends HttpServlet implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        // Set session timeout to  10 seconds  (30 min minutes 1800 seconds)
        // in session created we can even state the Context, but I would avoid doing this since new session is started everytime when we
        //1. start the app
        //2. logout and continue useing the app logged out.
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        // change the userState bach to anonymous
        event.getSession().getServletContext().setAttribute("userState", "anonymous");
        // if you remove the comments of the next session you will see everything in our context and that even includes the info from the xml about our configuration
        /*Enumeration<String> attributeNames = event.getSession().getServletContext().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            // Print the attribute name and its value
            System.out.println("Attribute name: " + name + ", value: " + event.getSession().getServletContext().getAttribute(name));
        }*/
    }
}
