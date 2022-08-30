<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <meta charset="UTF-8">
  <title>Registration</title>

  <script type="text/javascript">
    <%@include file="/WEB-INF/js/validate_passwords.js"%>
  </script>

  <style>
    <%@ include file="../css/button_location.css" %>
    <%@ include file="../css/form_design.css"%>
    <%@ include file="../css/button_attributes.css"%>


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
<h1 class="welcome-message" style="text-align: center; background: #42D583">Registration form</h1>
<div class="sign_in">
  Already have an account?
  <form action="signIn">
    <input type="submit" value="Sign in">
  </form>
</div>


<hr>

<div class="registration-cssave">
  <form style="text-align: right" method="post" action="newUser" onsubmit="return checkPassword(this)">
    <h3 class="move_h3">Registration form</h3>
    <div class="form-group">
      <input class="form-control item" type="text" name="firstName" id="firstName" pattern="[A-ZА-Яa-zа-я]{2,20}"
             placeholder="First name" title="Only one capital letter" required>
    </div>
    <div class="form-group">
      <input class="form-control item" type="text" name="lastName" id="lastName" pattern="[A-Za-zА-Яа-я]{1,25}"
             placeholder="Last name" title="Only latin alphabet with one capital letter" required>
    </div>
    <div class="form-group">
      <input class="form-control item" type="email" name="login" id="username" pattern="^[A-Za-z0-9._-]+@[a-z]+[.][a-z]+$"
             placeholder="Login" title="Should be like example@gmail.com" required>
    </div>

    <c:if test = "${requestScope.emailExists != null}">
      <div class="message">
          ${requestScope.emailExists}
      </div>
    </c:if>

    <div class="form-group">
      <input class="form-control item" type="password" name="password" minlength="4" maxlength="30" id="password"
             placeholder="Password" title="Min length - 4" required>
    </div>
    <div class="form-group">
      <input class="form-control item" type="password" name="submitPassword" minlength="4" maxlength="30" id="submitPassword"
             placeholder="Submit password" title="Min length - 4" required>
    </div>
    <div class="form-group">
      <button class="create-account color" type="submit">Login the account</button>
    </div>
  </form>
</div>
<hr>

</body>
</html>