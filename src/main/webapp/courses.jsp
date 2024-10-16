<%--
  Created by IntelliJ IDEA.
  User: sarah
  Date: 2024-10-08
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="/fragments/header.jsp" %>

<c:set var="viewTitle" value="All Courses" scope="request" />
<%@ include file="/fragments/tableView.jsp" %>


<%@ include file="/fragments/footer.jsp" %>