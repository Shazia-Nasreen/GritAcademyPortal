<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<form class="form-container" id="associationForm" action=/mypage method="POST">
  <div class="form-group">
    <select class="form-select" name="idChosenTeacher">
      <c:choose>
        <c:when test="${fn:length(coursesData)<=1}">
          <option selected disabled value="">No data available.</option>
        </c:when>
        <c:otherwise>
          <option selected disabled>Who do you want to change</option>
          <c:forEach items="${coursesData}" var="row" varStatus="loopRow">
            <c:if test="${loopRow.index != 0}">
              <c:forEach items="4" var="column" varStatus="loop">
                <option value="${row[0]}">id ${row[0]}: ${row[1]} ${row[2]} --> ${row[3]}</option>
              </c:forEach>
            </c:if>
          </c:forEach>
        </c:otherwise>
      </c:choose>
    </select>
  </div>
  <button value="changePrivilege" name="personCourseSubmit" class="btn btn-primary" type=submit>Change privilege</button>
</form>
</div>