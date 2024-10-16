package servlets;

import models.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Servlet that handles login functionality for students and teachers.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet implements HttpSessionListener {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve current state
        String currState = (String) getServletContext().getAttribute("userState");
        req.getSession().setAttribute("errorMessage", "");

        // Check if the user is logged in or not, and forward accordingly
        if (currState == null || currState.equals("anonymous")) {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else if (currState.equals("confirmed")) {
            req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set content type
        resp.setContentType("text/html");

        // Retrieve login form parameters
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String userType = req.getParameter("user_type");

        // Validate form inputs
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            req.getSession().setAttribute("errorMessage", "Username and password cannot be empty.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        if (userType == null || (!userType.equals("student") && !userType.equals("teacher"))) {
            req.getSession().setAttribute("errorMessage", "Invalid user type.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        // Perform login validation
        boolean success = performLogin(req, userType, username, password);

        // Determine the result of login and respond accordingly
        if (success) {
            // Successful login
            getServletContext().setAttribute("userState", "confirmed");
            req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
        } else {
            // Failed login, return to login page with an error message
            req.getSession().setAttribute("errorMessage", "Invalid username or password.");
            getServletContext().setAttribute("userState", "anonymous");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    /**
     * Helper method to handle login logic for students and teachers.
     *
     * @param req The HttpServletRequest.
     * @param userType The type of user ("student" or "teacher").
     * @param username The entered username.
     * @param password The entered password.
     * @return true if login is successful, false otherwise.
     */
    private boolean performLogin(HttpServletRequest req, String userType, String username, String password) {
        LinkedList<String[]> data;
        if (userType.equals("student")) {
            data = DBConnector.getConnector().selectQuery("studentLogin", username, password);
            if (data.size() > 1) {
                // Initialize student user session
                UserBean userBean = createUserBean(data.get(1), USER_TYPE.student);
                req.getSession().setAttribute("loggedStudName", userBean.getName());
                req.getSession().setAttribute("userBean", userBean);
                req.getSession().setMaxInactiveInterval(300); // 5 minutes timeout
                return true;
            }
        } else if (userType.equals("teacher")) {
            data = DBConnector.getConnector().selectQuery("teacherLogin", username, password);
            if (data.size() > 1) {
                // Initialize teacher user session
                UserBean userBean = createUserBean(data.get(1), USER_TYPE.teacher);
                req.getSession().setAttribute("loggedTeachName", userBean.getName());
                req.getSession().setAttribute("userBean", userBean);
                req.getSession().setMaxInactiveInterval(300); // 5 minutes timeout
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to create a UserBean from retrieved database data.
     *
     * @param userData The array of user data retrieved from the database.
     * @param userType The type of user (student or teacher).
     * @return A populated UserBean.
     */
    private UserBean createUserBean(String[] userData, USER_TYPE userType) {
        UserBean userBean = new UserBean(userData[0], userType, "user", STATE_TYPE.confirmed);
        userBean.setName(userData[1] + " " + userData[2]); // Set full name
        return userBean;
    }
}
