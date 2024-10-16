<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<form id="searchBy" class="form-container" action="/mypage" method="post">
  <h2>Display information for course(s)</h2>
  <div class="form-group">
    <label for="name">Course search name:</label>
    <input type="text" id="name" class="form-input" name="name">
  </div>

  <div class="form-group">
    <label for="id">Course ID:</label>
    <input type="text" id="id" class="form-input" name="id">
  </div>

  <button type="submit" value="courseInfoSubmit" name="personCourseSubmit" class="btn btn-primary">Check</button>
</form>
