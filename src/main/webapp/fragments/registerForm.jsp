<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<div class="form-container">
    <h2>Registration Form</h2>
    <form action="/register" method="post">

        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" class="form-input" required>
        </div>

        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" class="form-input" required>
        </div>

        <div class="form-group">
            <label for="town">Town:</label>
            <input type="text" id="town" name="town" class="form-input">
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" class="form-input">
        </div>

        <div class="form-group">
            <label for="phone">Phone:</label>
            <input type="tel" id="phone" name="phone" class="form-input">
        </div>

        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" class="form-input" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" class="form-input" required>
        </div>

        <div class="form-group">
            <label for="userType">User Type:</label><br>
            <select id="userType" name="userType" class="form-select" required>
                <option value="">Select User Type</option>
                <option value="student">Student</option>
                <option value="teacher">Teacher</option>
            </select>
        </div>

        <button type="submit" class="btn">Register</button>
    </form>
</div>