<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<form id="searchBy" class="form-container" action="/mypage" method="post">
    <h2>Register a person to a course.</h2>
    <div class="form-group">
        <label for="id">ID Person:</label>
        <input type="text" id="id" class="form-input" name="id">
    </div>

    <div class="form-group">
        <label for="add_for">Student or teacher:</label>
        <select id="add_for" class="form-select" name="add_for">
            <option value="student">Student</option>
            <option value="teacher">Teacher</option>
        </select>
    </div>

    <button type="submit" value="addPersonCourse" name="personCourseSubmit" class="btn btn-primary">Manage</button>
</form>