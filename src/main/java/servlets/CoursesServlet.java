package servlets;



import models.DBConnector;
import models.PRIVILEGE_TYPE;
import models.USER_TYPE;
import models.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

@WebServlet("/courses")
public class CoursesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LinkedList<String[]> coursesData;
        LinkedList<String> tableHeaders;
        System.out.println(req.getServletContext().getAttribute("userState"));
        // Fetch course data using the selectQuery method
        coursesData = DBConnector.getConnector().selectQuery("showAllCourses");
        tableHeaders = buildTableHeaders("ID", "Name", "Points", "Description");
        // Set the course data as a request attribute
        req.setAttribute("coursesData", coursesData);
        req.setAttribute("tableHeaders", tableHeaders);

        // request to the courses.jsp for rendering
        req.getRequestDispatcher("/courses.jsp").forward(req,resp);
    }

    protected LinkedList<String> buildTableHeaders(String...args){
        LinkedList<String> tableHeaders = new LinkedList<>();
        tableHeaders.addAll(Arrays.asList(args));
        return tableHeaders;
    }
}