<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<div class="container">
  <h2><c:out value="${viewTitle}" /></h2>
  <table class="table">
    <thead>
    <tr>
      <c:forEach items="${tableHeaders}" var="column">
        <th>${column}</th>
      </c:forEach>
    </tr>
    </thead>
    <tbody>
    <c:choose>
      <c:when test="${fn:length(coursesData)==1}">
        <tr>
          <td colspan="${fn:length(tableHeaders)}">No data available.</td>
        </tr>
      </c:when>
      <c:otherwise>
        <c:forEach items="${coursesData}" var="row" varStatus="loopRow">
          <c:if test="${loopRow.index != 0}">
            <tr>
              <c:forEach items="${tableHeaders}" var="column" varStatus="loop">
                <td>${row[loop.index]}</td>
              </c:forEach>
            </tr>
          </c:if>
        </c:forEach>
      </c:otherwise>
    </c:choose>
    </tbody>
  </table>
</div>