<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course List</title>
    <!-- Add Bootstrap for styling if needed -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>

    <div class="container">
        <h2>Course List</h2>
        <table class="table table-bordered">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Points</th>
            </tr>
            </thead>
            <tbody>
            <!-- Iterate over the coursesData list -->
            <c:forEach items="${coursesData}" var="course" varStatus="status">
                <tr>
                    <td>${course.id}</td> <!-- course ID -->
                    <td>${course.name}</td> <!-- course name -->
                    <td>${course.points}</td> <!-- course points -->
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>