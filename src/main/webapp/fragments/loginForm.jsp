<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<form id="loginForm" class="form-container" action="/login" method="post">
    <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" class="form-input" name="username" required>
    </div>

    <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" class="form-input" name="password" required>
    </div>

    <div class="form-group">
        <label for="user_type">Choose a user type:</label>
        <select id="user_type" class="form-select" name="user_type">
            <option value="student">Student</option>
            <option value="teacher">Teacher</option>
        </select>
    </div>

    <button type="submit" value="login" name="personCourseSubmit" class="btn btn-primary">Login</button>
</form>