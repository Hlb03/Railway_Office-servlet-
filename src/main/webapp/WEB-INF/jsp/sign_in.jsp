<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message" />

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="authorization.title"/></title>

    <style>
        <%@ include file="../css/button_location.css"%>
        <%@ include file="../css/form_design.css"%>
        <%@ include file="../css/button_attributes.css"%>
        <%@ include file="../css/locale_position.css"%>

        .registration-cssave .color {
            background-color: #3f93ff;
        }

        .move_h3 {
            position: relative;
            right: 50px;
        }

        .message {
            position: relative;
            color: red;
            right: 10px;
            font-size: 12px;
        }

    </style>

</head>
<body>
<h1 class="welcome-message" style="text-align: center; background: #3f93ff"><fmt:message key="authorization.heading"/></h1>

<fmt:message key="authorization.create_new_account" var="create_new"/>
<div class="sign_in">
    <form action="registration">
        <input type="submit" value="${create_new}">
    </form>
</div>

<div class="locale_position">
    <a href="signIn?cookieLocale=en">EN</a> |
    <a href="signIn?cookieLocale=uk">UK</a>
</div>

<hr>

<div class="registration-cssave">
    <form style="text-align: right" method="post" action="authorize">
        <h3 class="move_h3"><fmt:message key="authorization.log_in_form"/></h3>

        <c:if test = "${requestScope.failedAuthorize != null}">
            <div class="message">
                    ${requestScope.failedAuthorize}
            </div>
        </c:if>

        <fmt:message key="authorization.enter_login" var="login"/>
        <fmt:message key="authorization.enter_password" var="password"/>
        <div class="form-group">
            <input class="form-control item" type="email" name="login" id="username" pattern="^[A-Za-z0-9._-]+@[a-z]+[.][a-z]+$"
                   placeholder="${login}" title="example@gmail.com" required>
        </div>
        <div class="form-group">
            <input class="form-control item" type="password" name="password" minlength="4" maxlength="30" id="password"
                   placeholder="${password}" title="Min length - 4" required>
        </div>
        <div class="form-group">
            <button class="create-account color" type="submit"><fmt:message key="authorization.submit_button"/></button>
        </div>
    </form>
</div>
<hr>
</body>
</html>