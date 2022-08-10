<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Main menu</title>
    <style>
        .welcome-message{
            border-radius: 15px;
            background-color: chocolate;
            text-align: center;
        }
    </style>
</head>
<body>
<h1 class="welcome-message">Railway Office</h1>

<c:choose>
    <c:when test="${sessionScope.userLog == null}">
        <%@ include file="../jspf/heading_not_authorized.jspf" %>
    </c:when>
    <c:when test="${sessionScope.userLog != null}">
        <%@ include file="../jspf/heading_authorized.jspf" %>
    </c:when>
</c:choose>

</body>
</html>