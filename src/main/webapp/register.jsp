<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<form action="${pageContext.request.contextPath}/register" method="post">
    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" required />

    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" required />

    <label for="town">Town:</label>
    <input type="text" id="town" name="town" />

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required />

    <label for="phone">Phone:</label>
    <input type="tel" id="phone" name="phone" />

    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required />

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required />

    <label for="userType">Register as:</label>
    <select id="userType" name="userType">
        <option value="student">Student</option>
        <option value="teacher">Teacher</option>
    </select>

    <button type="submit">Register</button>
</form>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
</c:if>





<%@ include file="fragments/footer.jsp" %>
