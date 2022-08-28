<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Authorization</title>

    <style>
        <%@ include file="../css/button_location.css"%>
        <%@ include file="../css/form_design.css"%>
        <%@ include file="../css/button_attributes.css"%>

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
<h1 class="welcome-message" style="text-align: center; background: #3f93ff">Authorization form</h1>
<div class="sign_in">
    <form action="registration">
        <input type="submit" value="Create new account">
    </form>
</div>


<hr>

<div class="registration-cssave">
    <form style="text-align: right" method="post" action="authorize">
        <h3 class="move_h3">Log in form</h3>

        <c:if test = "${requestScope.failedAuthorize != null}">
            <div class="message">
                    ${requestScope.failedAuthorize}
            </div>
        </c:if>
        <div class="form-group">
            <input class="form-control item" type="email" name="login" id="username" pattern="^[A-Za-z0-9._-]+@[a-z]+[.][a-z]+$"
                   placeholder="Login" title="example@gmail.com" required>
        </div>
        <div class="form-group">
            <input class="form-control item" type="password" name="password" minlength="4" maxlength="30" id="password"
                   placeholder="Password" title="Min length - 4" required>
        </div>
        <div class="form-group">
            <button class="create-account color" type="submit">Login the account</button>
        </div>
    </form>
</div>
<hr>
</body>
</html>