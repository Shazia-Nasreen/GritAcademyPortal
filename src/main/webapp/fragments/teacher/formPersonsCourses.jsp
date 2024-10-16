<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<form id="searchBy" class="form-container" action="/mypage" method="post">
  <h2>Courses of Person</h2>
  <div class="form-group">
    <label for="fname">First name:</label>
    <input type="text" id="fname" class="form-input" name="fname">
  </div>

  <div class="form-group">
    <label for="lname">Last name:</label>
    <input type="text" id="lname" class="form-input" name="lname">
  </div>

  <div class="form-group">
    <label for="id">ID:</label>
    <input type="text" id="id" class="form-input" name="id">
  </div>

  <div class="form-group">
    <label for="search_for">Student or teacher:</label>
    <select id="search_for" class="form-select" name="search_for">
      <option value="student">Student</option>
      <option value="teacher">Teacher</option>
    </select>
  </div>

  <button type="submit" value="personCourseSubmit" name="personCourseSubmit" class="btn btn-primary">Check</button>
</form>