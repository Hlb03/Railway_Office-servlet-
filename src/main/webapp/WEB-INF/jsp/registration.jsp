<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message" />

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <meta charset="UTF-8">
  <title><fmt:message key="registration.title"/></title>

  <script type="text/javascript">
    <%@include file="/WEB-INF/js/validate_passwords.js"%>
  </script>

  <style>
    <%@ include file="../css/button_location.css" %>
    <%@ include file="../css/form_design.css"%>
    <%@ include file="../css/button_attributes.css"%>
    <%@ include file="../css/locale_position.css"%>

    .registration-cssave .color {
      background-color: #42D583;
    }

    .move_h3 {
      position: relative;
      right: 30px;
    }

    .message {
      position: relative;
      color: red;
      right: 20px;
      bottom: 25px;
      font-size: 12px;
    }
  </style>

</head>
<body>
<h1 class="welcome-message" style="text-align: center; background: #42D583"><fmt:message key="registration.heading"/></h1>
<div class="sign_in">
  <fmt:message key="registration.message"/>
  <fmt:message key="registration.sign_in" var="sign_in"/>
  <form action="signIn">
    <input type="submit" value="${sign_in}">
  </form>
</div>

<div class="locale_position">
  <a href="registration?cookieLocale=en">EN</a> |
  <a href="registration?cookieLocale=uk">UK</a>
</div>


<hr>

<fmt:message key="registration.first_name" var="first_name"/>
<fmt:message key="registration.last_name" var="last_name"/>
<fmt:message key="registration.login" var="login"/>
<fmt:message key="registration.password" var="password"/>
<fmt:message key="registration.submit_password" var="submit_password"/>

<div class="registration-cssave">
  <form style="text-align: right" method="post" action="newUser" onsubmit="return checkPassword(this)">
    <h3 class="move_h3"><fmt:message key="registration.registration_form"/></h3>
    <div class="form-group">
      <input class="form-control item" type="text" name="firstName" id="firstName" pattern="[A-ZА-Яa-zа-я]{2,20}"
             placeholder="${first_name}" title="Only one capital letter" required>
    </div>
    <div class="form-group">
      <input class="form-control item" type="text" name="lastName" id="lastName" pattern="[A-Za-zА-Яа-я]{1,25}"
             placeholder="${last_name}" title="Only latin alphabet with one capital letter" required>
    </div>
    <div class="form-group">
      <input class="form-control item" type="email" name="login" id="username" pattern="^[A-Za-z0-9._-]+@[a-z]+[.][a-z]+$"
             placeholder="${login}" title="Should be like example@gmail.com" required>
    </div>

    <c:if test = "${requestScope.emailExists != null}">
      <div class="message">
          ${requestScope.emailExists}
      </div>
    </c:if>

    <div class="form-group">
      <input class="form-control item" type="password" name="password" minlength="4" maxlength="30" id="password"
             placeholder="${password}" title="Min length - 4" required>
    </div>
    <div class="form-group">
      <input class="form-control item" type="password" name="submitPassword" minlength="4" maxlength="30" id="submitPassword"
             placeholder="${submit_password}" title="Min length - 4" required>
    </div>
    <div class="form-group">
      <button class="create-account color" type="submit"><fmt:message key="registration.submit_button"/></button>
    </div>
  </form>
</div>
<hr>

</body>
</html>