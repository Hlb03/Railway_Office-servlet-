<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<br>
<br>
<h2 style="text-align: center; color: red">Sorry, ${exception} </h2>
<%--${pageContext.getException()} ${pageContext.getException().getMessage}--%>
<%
    System.out.println(pageContext.getErrorData().getRequestURI());
    System.out.println("<br/>");
    System.out.println(pageContext.getErrorData().getStatusCode());
    System.out.println("<br/>");
    System.out.println(pageContext.getException());
    System.out.println("<br/>");
    System.out.println(pageContext.getException().getMessage());
%>
</body>
</html>
