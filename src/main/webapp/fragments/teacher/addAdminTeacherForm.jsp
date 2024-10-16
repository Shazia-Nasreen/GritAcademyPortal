<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<div class="form-container">
  <h2>Add Admin Teacher Form</h2>
  <form action="/mypage?sub=add-admin-teacher" method="post">

    <div class="form-group">
      <label for="teacherId">Teacher ID:</label>
      <input type="text" id="teacherId" name="teacherId" class="form-input" required>
    </div>

    <div class="form-group">
      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" name="firstName" class="form-input" required>
    </div>

    <div class="form-group">
      <label for="lastName">Last Name:</label>
      <input type="text" id="lastName" name="lastName" class="form-input" required>
    </div>

    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" class="form-input" required>
    </div>

    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" class="form-input" required>
    </div>

    <div class="form-group">
      <label for="username">Username:</label>
      <input type="text" id="username" name="username" class="form-input" required>
    </div>

    <button type="submit" class="btn">Add Admin Teacher</button>
  </form>
</div>