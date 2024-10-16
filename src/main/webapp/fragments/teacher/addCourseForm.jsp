<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<div class="form-container">
    <h2>Add new course</h2>
    <form action="/mypage" method="post">

        <div class="form-group">
            <label for="courseName">Course Name:</label>
            <input type="text" id="courseName" name="courseName" class="form-input" required>
        </div>

        <div class="form-group">
            <label for="points">Points:</label>
            <input type="number" id="points" name="points" class="form-input" min="5" required>
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description" class="form-input" ></textarea>
        </div>

        <button type="submit" value="addCourse" name="personCourseSubmit" class="btn">Add Course</button>
    </form>
</div>