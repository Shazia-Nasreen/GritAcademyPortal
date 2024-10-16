<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="fragments/header.jsp" %>
<!-- here we have to ensure that state and userType hold expected values in order to show the user the expected navbar-->
<c:choose>

  <c:when test="${userBean.userType == 'student' && userBean.stateType == 'confirmed'}">
    <c:set var="viewTitle" value="${loggedStudName}" scope="request" />

    <%@ include file="fragments/student/navStudent.jsp" %>

    <h2> You are logged in as student: <c:out value="${viewTitle}"/></h2>

    <c:choose>
      <c:when test="${caller == 'studentCourses'}">
        <c:set var="viewTitle" value="All Courses" scope="request" />
        <%@ include file="fragments/tableView.jsp" %>
      </c:when>
      <c:when test="${caller == 'studentClass'}">
        <c:set var="viewTitle" value="Class-mates" scope="request" />
        <%@ include file="fragments/tableView.jsp" %>
      </c:when>
      <c:when test="${caller == 'error'}">
        <%@ include file="fragments/404.jsp" %>
      </c:when>
    </c:choose>
  </c:when>

  <c:when test="${userBean.userType == 'teacher' && userBean.privilegeType == 'user'&& userBean.stateType == 'confirmed'}">
    <c:set var="viewTitle" value="${loggedTeachName}" scope="request" />

    <%@ include file="fragments/teacher/navUserTeacher.jsp" %>

    <h2> You are logged in as teacher: <c:out value="${viewTitle}"/></h2>

    <c:choose>
      <c:when test="${caller == 'teacherAllCourses'}">
        <c:set var="viewTitle" value="All Courses" scope="request" />
        <%@ include file="fragments/tableView.jsp" %>
      </c:when>

      <c:when test="${caller == 'teacherAllStudents'}">
        <c:set var="viewTitle" value="All Students" scope="request" />
        <%@ include file="fragments/tableView.jsp" %>
      </c:when>

      <c:when test="${caller == 'teacherCoursesOfPerson'}">
        <%@ include file="fragments/teacher/formPersonsCourses.jsp" %>
      </c:when>

      <c:when test="${caller == 'teacherCourseInformation'}">
        <%@ include file="fragments/teacher/formCourseInformation.jsp" %>
      </c:when>

      <c:when test="${caller == 'error'}">
        <%@ include file="fragments/404.jsp" %>
      </c:when>
    </c:choose>

  </c:when>

  <c:when test="${userBean.userType == 'teacher' && userBean.privilegeType == 'admin'&& userBean.stateType == 'confirmed'}">

    <c:set var="viewTitle" value="${loggedTeachName}" scope="request" />

    <%@ include file="fragments/teacher/navAdminTeacher.jsp" %>

    <h2> You are logged in as teacher: <c:out value="${viewTitle}"/></h2>

    <c:choose>
      <c:when test="${caller == 'teacherAllCourses'}">
        <c:set var="viewTitle" value="All Courses" scope="request" />
        <%@ include file="fragments/tableView.jsp" %>
      </c:when>

      <c:when test="${caller == 'teacherAllStudents'}">
        <c:set var="viewTitle" value="All Students" scope="request" />
        <%@ include file="fragments/tableView.jsp" %>
      </c:when>

      <c:when test="${caller == 'teacherCoursesOfPerson'}">
        <%@ include file="fragments/teacher/formPersonsCourses.jsp" %>
      </c:when>

      <c:when test="${caller == 'teacherCourseInformation'}">
        <%@ include file="fragments/teacher/formCourseInformation.jsp" %>
      </c:when>

      <c:when test="${caller == 'teacherAddCourse'}">
        <%@ include file="fragments/teacher/addCourseForm.jsp" %>
      </c:when>

      <c:when test="${caller == 'teacherRegisterTeacherForCourse'}">
        <p>To be implemented Dropdowns and Table</p>
      </c:when>

      <c:when test="${caller == 'teacherRegisterStudentInCourse'}">
        <%@ include file="fragments/teacher/formAssignPersonToClass.jsp" %>
        <c:set var="viewTitle" value="All students list" scope="request" />
        <%@ include file="fragments/tableView.jsp" %>
      </c:when>

      <c:when test="${caller == 'remove'}">
        <p>To be implemented Depends on what we will remove</p>
      </c:when>

      <c:when test="${caller == 'error'}">
        <%@ include file="fragments/404.jsp" %>
      </c:when>

    </c:choose>
  </c:when>

  <c:when test="${userBean.userType == 'teacher' && userBean.privilegeType == 'superadmin' && userBean.stateType == 'confirmed'}">

    <c:set var="viewTitle" value="${loggedTeachName}" scope="request" />

    <%@ include file="fragments/teacher/navSuperadmin.jsp" %>

    <h2> You are logged in as teacher: <c:out value="${viewTitle}"/></h2>

    <c:choose>
      <c:when test="${caller == 'makeAdmin'}">
        <%@ include file="fragments/teacher/formChangePrivilege.jsp" %>
        <%@ include file="fragments/tableView.jsp" %>
      </c:when>

      <c:when test="${caller == 'reports'}">
        <c:set var="viewTitle" value="Statistics" scope="request" />
        <%@ include file="fragments/tableView.jsp" %>
      </c:when>

      <c:when test="${caller == 'error'}">
        <%@ include file="fragments/404.jsp" %>
      </c:when>
    </c:choose>
  </c:when>

  <c:otherwise>
    <c:if test="${userState == 'anonymous'}">
      <p>Not logged in</p>
    </c:if>
  </c:otherwise>
</c:choose>

<c:if test="${caller == 'answerRequest'}">

  <c:choose>

    <c:when test="${personCourseSubmit == 'personCourseSubmit'}">
      <c:set var="viewTitle" value="Show all courses per person" scope="request" />
    </c:when>

    <c:when test="${personCourseSubmit == 'courseInfoSubmit'}">
      <c:set var="viewTitle" value="Show course information per course" scope="request" />
    </c:when>

    <c:when test="${personCourseSubmit == 'addPersonCourse'}">
      <c:if test="${foundPerson != 'no'}">
        <c:set var="viewTitle" value="Chosen student's current enrolls" scope="request" />
        <%@ include file="fragments/teacher/formAssignPersonToClass.jsp" %>
        <%@ include file="fragments/teacher/dropDownAvailableCourses.jsp" %>
      </c:if>
      <c:if test="${foundPerson == 'no'}">
        <c:set var="viewTitle" value="All students list" scope="request" />
        <%@ include file="fragments/teacher/formAssignPersonToClass.jsp" %>
        <%@ include file="fragments/teacher/dropDownAvailableCourses.jsp" %>
      </c:if>
    </c:when>

    <c:when test="${personCourseSubmit == 'registerForCourse'}">
      <c:set var="viewTitle" value="Test" scope="request" />
    </c:when>

    <c:otherwise>
      <c:set var="viewTitle" value="Result set" scope="request" />
    </c:otherwise>

  </c:choose>

  <%@ include file="fragments/tableView.jsp" %>

</c:if>

<%@ include file="fragments/footer.jsp" %>