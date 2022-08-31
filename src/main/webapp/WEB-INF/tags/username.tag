<%@ tag language="java" pageEncoding="UTF-8" %>

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
<h3><i>You are logged as - <strong>${sessionScope.userNS} (${sessionScope.userLogin})</strong></i></h3>
