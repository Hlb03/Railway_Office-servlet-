<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.errorMsg != null}">
    <% session.setAttribute("errorMsg", null); %>
</c:if>

