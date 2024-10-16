<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<<form class="form-container" id="associationForm" action=/mypage method="POST">
<div class="form-group">
  <label for="course">Choose a user type:</label>
  <select id="course" class="form-select" name=idCourse>
    <c:choose>
      <c:when test="${fn:length(availableCourses)<2}">
        <option selected disabled value="">No data available.</option>
      </c:when>
      <c:otherwise>
        <option selected disabled>Register for course</option>
        <c:forEach items="${availableCourses}" var="row" varStatus="loopRow">
          <c:if test="${loopRow.index != 0}">
            <c:forEach items="4" var="column" varStatus="loop">
              <option value="${row[0]}">${row[1]}</option>
            </c:forEach>
          </c:if>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </select>
</div>
<button value="registerForCourse" name="personCourseSubmit" class="btn btn-primary" type=submit>Enroll</button>
</form>
</div>