package servlets;

import models.DBConnector;
import models.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve userBean and state from session
        HttpSession session = req.getSession(false);  // Use false to avoid creating a new session if none exists
        if (session == null || session.getAttribute("userBean") == null) {
            // Handle case where session is invalid or expired
            req.getRequestDispatcher("loggedout.jsp").forward(req, resp);
            return;
        }

        // Forward to registration page
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String town = request.getParameter("town");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        String state = (String) getServletContext().getAttribute("userState");

        // Validate form inputs
        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Names, username, and password cannot be empty!");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Ensure state is valid
        if (state == null || !state.equals("confirmed")) {
            request.getRequestDispatcher("loggedout.jsp").forward(request, response);
            return;
        }

        boolean registrationSuccessful = false;
        try {
            if (userType.equals("student")) {
                registrationSuccessful = DBConnector.getConnector().insertQuery("registerNewStudent",
                        firstName, lastName, town, email, phone, username, password, "S", "S", "S", "S", "S", "S", "S");
            } else if (userType.equals("teacher")) {
                registrationSuccessful = DBConnector.getConnector().insertQuery("registerNewTeacher",
                        firstName, lastName, town, email, phone, username, password, "S", "S", "S", "S", "S", "S", "S");
            } else {
                request.setAttribute("errorMessage", "Invalid user type selected.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Registration failed due to an error: " + e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (registrationSuccessful) {
            // Registration was successful, forward to the user page
            request.getRequestDispatcher("myPage.jsp").forward(request, response);
        } else {
            // Registration failed, notify user
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
