<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message" />

<%@ attribute
        name="userName"
        type="java.lang.String"
        required="true"
%>

<%@ attribute
        name="userLogin"
        type="java.lang.String"
        required="true"
%>

<%--        rtexprvalue="true"--%>
<h3><i><fmt:message key="menu.main.logged_as"/> - <strong>${sessionScope.userNS} (${sessionScope.userLogin})</strong></i></h3>
