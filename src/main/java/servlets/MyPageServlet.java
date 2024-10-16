package servlets;

import models.DBConnector;
import models.UserBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;

@WebServlet("/mypage")
public class MyPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBean userBeanCon = (UserBean) getServletContext().getAttribute("userBean");
        UserBean userBeanSess = null;
        String userId = "0";

        try {
            userBeanSess = (UserBean) req.getSession().getAttribute("userBean");
            userId = userBeanSess.getId();
        } catch (NullPointerException ex) {

        }
        String state = (String) getServletContext().getAttribute("userState");
        if (state != null && state.equals("confirmed") && userBeanSess != null) {
            String userType = String.valueOf(userBeanSess.getUserType());
            LinkedList<String[]> databaseData;
            LinkedList<String> tableHeaders;
            // userBean is stored in session after login. The one in Context stays as anonymous
            // Now the correct data can be retrieved from the bean
            UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
            String privilegeType = String.valueOf(userBean.getPrivilegeType());
            // Here we will find if the user is student, super admin, or teacher in that order
            String comingFromSubMenu = req.getParameter("sub");
            Boolean isConfirmed = req.getServletContext().getAttribute("userState").equals("confirmed");
            if (!userType.equals("teacher")) {
                if (comingFromSubMenu != null && comingFromSubMenu.equals("my-courses")) {
                    String student = "studentCourses";
                    // set the context attribute to find next action in myPage
                    req.getSession().setAttribute("caller", student);
                    if(isConfirmed) {
                        databaseData = DBConnector.getConnector().selectQuery("EnrolledCoursesOverview", userId);
                        //System.out.println(Arrays.toString(Arrays.stream(databaseData.get(0)).toArray()));
                        tableHeaders = buildTableHeaders("ID Course", "Course", "Points", "Description", "Teacher(s)");
                        req.getSession().setAttribute("coursesData", databaseData);
                        req.getSession().setAttribute("tableHeaders", tableHeaders);
                        req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Cannot show all student's courses because the session expired");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                } else if (comingFromSubMenu != null && comingFromSubMenu.equals("my-classmates")) {
                    String student = "studentClass";
                    // set the context attribute to find next action in myPage
                    req.getSession().setAttribute("caller", student);
                    if(isConfirmed) {
                        databaseData = DBConnector.getConnector().selectQuery("ClassMates", userId);
                        tableHeaders = buildTableHeaders("Course Name", "ClassMates Name");
                        req.getSession().setAttribute("coursesData", databaseData);
                        req.getSession().setAttribute("tableHeaders", tableHeaders);
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Cannot show all student's courses because the session expired");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                } else if (comingFromSubMenu != null) {
                    String student = "error";
                    req.getSession().setAttribute("caller", student);
                    req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                } else {
                    if(isConfirmed) {
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Student Session expired");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                }
            } else if (privilegeType.equals("superadmin")) {
                if (comingFromSubMenu != null && comingFromSubMenu.equals("manage-admins")) {
                    String teacher = "makeAdmin";
                    // set the context attribute to find next action in myPage
                    req.getSession().setAttribute("caller", teacher);
                    // display form and table with teachers that are user or admins
                    if(isConfirmed) {
                        databaseData = DBConnector.getConnector().selectQuery("getPrivileges");
                        tableHeaders = buildTableHeaders("ID teacher", "First name", "Last name", "Current privilege");
                        req.getSession().setAttribute("coursesData", databaseData);
                        req.getSession().setAttribute("tableHeaders", tableHeaders);
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Cannot show privileges.");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                } else if (comingFromSubMenu != null && comingFromSubMenu.equals("reports")) {
                    //TODO show all of the students classmates by course
                    String teacher = "reports";
                    // set the context attribute to find next action in myPage
                    req.getSession().setAttribute("caller", teacher);
                    if(isConfirmed) {
                        databaseData = DBConnector.getConnector().selectQuery("statistics");
                        //System.out.println(Arrays.toString(Arrays.stream(databaseData.get(0)).toArray()));
                        tableHeaders = buildTableHeaders("Average number of courses taken by students", "Courses  name in order of popularity", "num_students", "student_id", "Student  takes more courses");
                        req.getSession().setAttribute("coursesData", databaseData);
                        req.getSession().setAttribute("tableHeaders", tableHeaders);
                        System.out.println("Teacher superadmin statistics to be displayed");
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Cannot load statistical data. The session has expired");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                } else if (comingFromSubMenu != null) {
                    String student = "error";
                    req.getSession().setAttribute("caller", student);
                    System.out.println("error");
                    req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                } else {
                    if(isConfirmed) {
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("SuperAdmin session has expired");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                }
            } else {
                /**
                 Available for both teacher user and teacher admin but not for teacher super admin
                 **/
                // check from which submenu the teacher is coming. First take the parameter and assign it to a vriable

                if (comingFromSubMenu != null && comingFromSubMenu.equals("all-courses")) {
                    // change the variable caller that will send info about who is asking for what to the JSP
                    // based on it the JSP will know what to do after and which HTML elemets to include or build
                    String teacher = "teacherAllCourses";
                    req.getSession().setAttribute("caller", teacher);
                    if(isConfirmed) {
                        databaseData = DBConnector.getConnector().selectQuery("showAllCourses");
                        req.getSession().setAttribute("coursesData", databaseData);
                        // prepare the result table headers. We have to know in advance what we want to show in the table
                        // send them via context or session
                        tableHeaders = buildTableHeaders("ID", "Course Name", "Points", "Description");
                        req.getSession().setAttribute("tableHeaders", tableHeaders);
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Cannot show all courses. Session expired");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                } else if (comingFromSubMenu != null && comingFromSubMenu.equals("all-students")) {
                    /**
                     Available for both teacher user and teacher admin but not for teacher super admin
                     **/
                    String teacher = "teacherAllStudents";
                    req.getSession().setAttribute("caller", teacher);
                    if(isConfirmed) {
                        databaseData = DBConnector.getConnector().selectQuery("showStudents");
                        req.getSession().setAttribute("coursesData", databaseData);
                        tableHeaders = buildTableHeaders("ID", "First name", "Last name", "city", "email", "phone");
                        req.getSession().setAttribute("tableHeaders", tableHeaders);
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Cannot show all students. Session expired");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                } else if (comingFromSubMenu != null && comingFromSubMenu.equals("all-courses-of-person")) {
                    /**
                     Available for both teacher user and teacher admin but not for teacher super admin
                     **/
                    String teacher = "teacherCoursesOfPerson";
                    req.getSession().setAttribute("caller", teacher);
                    // display all courses that a student take. Provide student id or names.
                    if(isConfirmed) {
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Session expired. Cannot go to form for courses of person");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                } else if (comingFromSubMenu != null && comingFromSubMenu.equals("add-course")) {
                    /**
                     Available for both teacher user and teacher admin but not for teacher super admin
                     **/
                    String teacher = "teacherAddCourse";
                    req.getSession().setAttribute("caller", teacher);
                    if(isConfirmed) {
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Cannot go to Add course form. Session expired");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                } else if (comingFromSubMenu != null && comingFromSubMenu.equals("course-information")) {
                    /**
                     Available only for teacher admin
                     **/
                    String teacher = "teacherCourseInformation";
                    req.getSession().setAttribute("caller", teacher);
                    if(isConfirmed) {
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Cannot  show course information form.");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                } else if (comingFromSubMenu != null && comingFromSubMenu.equals("enroll")) {
                    /**
                     Available only for teacher admin
                     **/
                    String teacher = "teacherRegisterStudentInCourse";
                    req.getSession().setAttribute("caller", teacher);
                    if(isConfirmed) {
                        LinkedList<String[]> students = DBConnector.getConnector().selectQuery("showStudents");
                        LinkedList<String> theirHeaders = buildTableHeaders("ID", "First name", "Last name", "City", "Email", "Phone");
                        req.getSession().setAttribute("coursesData", students);
                        req.getSession().setAttribute("tableHeaders", theirHeaders);
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Cannot load form for association.");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                } else if (comingFromSubMenu != null && comingFromSubMenu.equals("remove")) {
                    /**
                     Available only for teacher admin
                     **/
                    String teacher = "remove";
                    req.getSession().setAttribute("caller", teacher);
                    //TODO implemet the deletion of something form the databsae.
                    System.out.println("Admin remove");
                    req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                } else if (comingFromSubMenu != null) {
                    String student = "error";
                    req.getSession().setAttribute("caller", student);
                    System.out.println("error");
                    if(isConfirmed) {
                        req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                    } else {
                        System.out.println("Good bye");
                        req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                    }
                } else {
                    req.getRequestDispatcher("myPage.jsp").forward(req, resp);
                }
            }
        } else {
            // User is not logged in, redirect to login page.
            resp.sendRedirect("login.jsp");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LinkedList<String[]> databaseData = new LinkedList<>();
        LinkedList<String> tableHeaders = new LinkedList<>();
        //TODO THE TEACHER DOES NOT SHOW CORRECTLY. FIX before presentation
        String sentFromPost = req.getParameter("personCourseSubmit");
        // student and teacher are using the same fields for search but are displaying different results.
        Boolean isConfirmed = req.getServletContext().getAttribute("userState").equals("confirmed");
        if (sentFromPost.equals("personCourseSubmit")) {
            String id = req.getParameter("id").trim();
            String fname = req.getParameter("fname").trim();
            String lname = req.getParameter("lname").trim();
            String searchFor = req.getParameter("search_for");
            System.out.println(searchFor);
            Boolean success = false;
            if (searchFor.equals("student")) {
                // the first ifs up to ELSE will handle the allowed cases and will display result based on which query was caled
                if (!id.isEmpty() && (!fname.isEmpty() && !lname.isEmpty())) {
                    tableHeaders = buildTableHeaders("ID", "Student", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showRegistrationsWithId", req.getParameter("fname"), req.getParameter("lname"), req.getParameter("id"));
                    success = true;
                } else if (!id.isEmpty() && fname.isEmpty() && lname.isEmpty()) {
                    tableHeaders = buildTableHeaders("ID", "Student", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showRegistrationsIdOnly", req.getParameter("id"));
                    success = true;
                } else if (id.isEmpty() && (!fname.isEmpty() && !lname.isEmpty())) {
                    tableHeaders = buildTableHeaders("ID", "Student", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showRegistrationsName", req.getParameter("fname"), req.getParameter("lname"));
                    success = true;
                } else {
                    // in here we are only checking the possible errors (one name field was left empty or all fields were left empty)
                    if ((fname.isEmpty() && !lname.isEmpty()) || (!fname.isEmpty() && lname.isEmpty())) {
                        String errorMsg = "Both names should be filled";
                        if (!errorMsg.isEmpty()) {
                            //out.println("<p class=error>"+errorMsg+"</p>");
                            System.out.println(errorMsg);
                            errorMsg = "";
                        }
                    } else if (fname.isEmpty() && lname.isEmpty() && id.isEmpty()) {
                        System.out.println("all fields are empty");
                    }
                    // at the end we will set the result data into the lists
                    tableHeaders = buildTableHeaders("ID", "First name", "Last name", "City", "Email");
                    databaseData = DBConnector.getConnector().selectQuery("showStudents");
                }
            } else if (searchFor.equals("teacher")) {
                if (!id.isEmpty() && (!fname.isEmpty() && !lname.isEmpty())) {
                    tableHeaders = buildTableHeaders("ID", "Teacher", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showCoursesForTeacherWithId", req.getParameter("fname"), req.getParameter("lname"), req.getParameter("id"));
                    success = true;
                } else if (!id.isEmpty() && fname.isEmpty() && lname.isEmpty()) {
                    tableHeaders = buildTableHeaders("ID", "Teacher", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showCoursesForTeacherIdOnly", req.getParameter("id"));
                    success = true;
                } else if (id.isEmpty() && (!fname.isEmpty() && !lname.isEmpty())) {
                    tableHeaders = buildTableHeaders("ID", "Teacher", "Course", "Points");
                    databaseData = DBConnector.getConnector().selectQuery("showCoursesForTeacherName", req.getParameter("fname"), req.getParameter("lname"));
                    success = true;
                } else {
                    if ((fname.isEmpty() && !lname.isEmpty()) || (!fname.isEmpty() && lname.isEmpty())) {
                        String errorMsg = "Both names should be filled";
                        if (!errorMsg.isEmpty()) {
                            //out.println("<p class=error>"+errorMsg+"</p>");
                            System.out.println(errorMsg);
                            errorMsg = "";
                        }
                    } else if (fname.isEmpty() && lname.isEmpty() && id.isEmpty()) {
                        System.out.println("all fields are empty");
                    }
                    tableHeaders = buildTableHeaders("ID", "First name", "Last name", "City", "Email");
                    databaseData = DBConnector.getConnector().selectQuery("showTeachers");
                }
                String teacher = "answerRequest";
                req.getSession().setAttribute("caller", teacher);
                req.getSession().setAttribute("personCourseSubmit", sentFromPost);
            } else {
                System.out.println("Cannot show information for student or teacher because session timmed out");
                req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
            }
            // after we have set the data either for teacher or for student we have to send it via session.
            //answerRequest
            if(success) {
                String teacher = "answerRequest";
                req.getSession().setAttribute("caller", teacher);
                req.getSession().setAttribute("personCourseSubmit", sentFromPost);
                req.getSession().setAttribute("coursesData", databaseData);
                req.getSession().setAttribute("tableHeaders", tableHeaders);
                req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
            } else {
                System.out.println("The could not be shown because of an error");
                req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
            }
        } else if (sentFromPost.equals("courseInfoSubmit")) {
            String id = req.getParameter("id").trim();
            if (id.isEmpty()) {
                id = "0";
            }
            String name = req.getParameter("name").trim();
            if (!name.trim().isEmpty()) {
                name = "%" + name + "%";
            }
            String teacher = "answerRequest";
            req.getSession().setAttribute("caller", teacher);
            req.getSession().setAttribute("personCourseSubmit", sentFromPost);
            if(isConfirmed){
                tableHeaders = buildTableHeaders("ID", "Course name", "Points", "Teachers", "Students");
                databaseData = DBConnector.getConnector().selectQuery("showCourseInformation", id, name);
                req.getSession().setAttribute("coursesData", databaseData);
                req.getSession().setAttribute("tableHeaders", tableHeaders);
                req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
            } else {
                System.out.println("Cannot show information about the courses of a person.");
                req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
            }

        } else if (sentFromPost.equals("addPersonCourse")) {
            /**
             * Part 1 of Functionality enroll student in course or teacher in class. Show tables and dropdown
             **/
            String teacher = "answerRequest";
            req.getSession().setAttribute("caller", teacher);
            req.getSession().setAttribute("personCourseSubmit", sentFromPost);
            String id = req.getParameter("id");
            if(isConfirmed){
                databaseData = DBConnector.getConnector().selectQuery("getSuggestions", id);
                req.getSession().setAttribute("results", databaseData);
                if (databaseData.size() > 1){
                    LinkedList<String[]> signedForCourses = DBConnector.getConnector().selectQuery("showRegistrationsIdOnly", id);
                    LinkedList<String> theirHeaders = buildTableHeaders("ID", "Course", "Teacher(s)", "Points");
                    req.getSession().setAttribute("coursesData", signedForCourses);
                    req.getSession().setAttribute("tableHeaders", theirHeaders);
                    LinkedList<String[]> availableCourses = DBConnector.getConnector().selectQuery("showAvailableCourses", id);
                    String foundPerson = id;
                    req.getSession().setAttribute("availableCourses", availableCourses);
                    req.getSession().setAttribute("foundPerson", foundPerson);
                } else {
                    req.getSession().removeAttribute("availableCourses");
                    LinkedList<String[]> students = DBConnector.getConnector().selectQuery("showStudents");
                    LinkedList<String> theirHeaders = buildTableHeaders("ID", "First name", "Last name", "City", "Email", "Phone");
                    String foundPerson = "no";
                    req.getSession().setAttribute("coursesData", students);
                    req.getSession().setAttribute("tableHeaders", theirHeaders);
                    req.getSession().setAttribute("foundPerson", foundPerson);
                }
                req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
            } else {
                System.out.println("The operation to prepare student for association was not successful");
                req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
            }

        } else if (sentFromPost.equals("registerForCourse")){
            /**
             * Part 2 of Functionality enroll student in course or teacher in class. Actual database add
             **/
            String teacher = "answerRequest";
            req.getSession().setAttribute("caller", teacher);
            if(isConfirmed){
                //registerStudentInCourse
                String idStudent = (String) req.getSession().getAttribute("foundPerson");
                String idCourse = req.getParameter("idCourse");
                DBConnector.getConnector().insertQuery("registerStudentInCourse", idStudent, idCourse,"I","I");
                req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
            } else {
                System.out.println("The operation associate student with course was not successful");
                req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
            }

        } else if (sentFromPost.equals("changePrivilege")){
            String idTeacher = req.getParameter("idChosenTeacher");
            String changePrivilegeTo ="";
            LinkedList<String[]> priv = DBConnector.getConnector().selectQuery("getPrivilege", idTeacher);
            if(priv.size()>1) {
                String curPriv = priv.get(1)[0];
                if(curPriv.equals("user")){
                    changePrivilegeTo = "'admin'";
                } else if (curPriv.equals("admin")){
                    changePrivilegeTo = "'user'";
                } else {
                    changePrivilegeTo = "";
                }
            }
            if(isConfirmed){
                if(!changePrivilegeTo.isEmpty()){
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:13306/GritAcademy","portalAdmin", "1234");
                        String sql = "UPDATE teachers SET privilege_type = "+changePrivilegeTo+" WHERE id = " + Integer.parseInt(idTeacher) +";";

                        try {
                            Statement statement = con.createStatement();
                            if(statement.executeUpdate(sql) > 0) {
                                req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
                            } else {

                                System.out.println("No update was made");
                                req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
                            }
                        } catch(SQLException ex) {
                            System.out.println(ex);
                        }
                        con.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } else {
                System.out.println("The teacher's privilege was not changed.");
                req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
            }
        } else if (sentFromPost.equals("addCourse")) {
            /**
             * Functionality available only for Teacher admin. Add new course into the database.
             **/
            String courseName = req.getParameter("courseName");
            String pointsStr = req.getParameter("points");

            if (!courseName.isEmpty() && !pointsStr.isEmpty()) {
                if (isConfirmed){
                    DBConnector.getConnector().insertQuery("addNewCourse", courseName, pointsStr, req.getParameter("description"), "S", "I", "S");
                    System.out.println("Course added successfully");
                    req.getRequestDispatcher("/myPage.jsp").forward(req, resp);
                } else {
                    System.out.println("the course was not added");
                    req.getRequestDispatcher("/loggedout.jsp").forward(req, resp);
                }
            }
        }
    }

    protected LinkedList<String> buildTableHeaders(String...args){
        LinkedList<String> tableHeaders = new LinkedList<>();
        tableHeaders.addAll(Arrays.asList(args));
        return tableHeaders;
    }
}