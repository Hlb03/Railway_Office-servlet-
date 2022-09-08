<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="routeInfo" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message"/>
<html>
<head>
    <script type="text/javascript">
        <%@include file="/WEB-INF/js/buy_message.js"%>
    </script>

    <title><fmt:message key="ticket_view.title"/></title>
    <style>
        <%@include file="/WEB-INF/css/locale_position.css"%>

        table, th, td {
            border:1px solid black;
        }

        .welcome-message{
            border-radius: 15px;
            background-color: darkgoldenrod;
            text-align: center; //chocolate
        }

        /* Button used to open the contact form - fixed at the bottom of the page */
        .ticket_amount-button {
            background-color: #555;
            color: white;
            padding: 16px 20px;
            border: none;
            cursor: pointer;
            opacity: 0.8;
            position: fixed;
            bottom: 23px;
            right: 28px;
            width: 280px;
        }

        /* The popup form - hidden by default */
        .form-popup-position {
            display: none;
            position: fixed;
            bottom: 0;
            right: 15px;
            border: 3px solid #f1f1f1;
            z-index: 9;
        }

        /* Add styles to the form container */
        .form-container {
            max-width: 300px;
            padding: 10px;
            background-color: white;
        }

        /* Full-width input fields */
        .form-container input[type=text], .form-container input[type=password] {
            width: 100%;
            padding: 15px;
            margin: 5px 0 22px 0;
            border: none;
            background: #f1f1f1;
        }

        /* When the inputs get focus, do something */
        .form-container input[type=text]:focus, .form-container input[type=password]:focus {
            background-color: #ddd;
            outline: none;
        }

        /* Set a style for the submit/login button */
        .form-container .btn {
            background-color: #04AA6D;
            color: white;
            padding: 16px 20px;
            border: none;
            cursor: pointer;
            width: 100%;
            margin-bottom:10px;
            opacity: 0.8;
        }

        /* Add a red background color to the cancel button */
        .form-container .cancel {
            background-color: red;
        }

        /* Add some hover effects to buttons */
        .form-container .btn:hover, .add_train-button:hover {
            opacity: 1;
        }
    </style>
    <script type="text/javascript">
        <%@include file="/WEB-INF/js/popup_buy_ticket_form.js"%>
        <%@include file="/WEB-INF/js/check_tickets_value.js"%>
        <%@include file="/WEB-INF/js/dynamic_price_generation.js"%>
    </script>

</head>
<body>
<h1 class="welcome-message"><fmt:message key="ticket_view.heading"/></h1>

<!-- Custom taglib -->
<my:routeInfoTag route="${requestScope.route}" locale="${cookie.lang}"/>

<c:if test="${sessionScope.userRole eq 'admin'}">
    <%@ include file="../jspf/admin_rights_for_ticket.jspf" %>
</c:if>

<div class="lang_location">
    <a href="${requestScope.url}&cookieLocale=en">EN</a> |
    <a href="${requestScope.url}&cookieLocale=uk">UK</a>
</div>

<c:if test="${requestScope.buyOpportunity == null && sessionScope.userRole ne 'admin'}">
    <button class="ticket_amount-button" onclick="openTicketAmountForm()"><fmt:message key="ticket_view.buy_ticket"/></button>
</c:if>

<!-- Popup form for adding new train -->
<div class="form-popup-position" id="ticketAmountForm">
        <c:choose>
            <c:when test="${sessionScope.userLogin == null}">
                <form action="buyTicket" class="form-container">
            </c:when>
            <c:when test="${sessionScope.userLogin != null}">
                <form action="buyTicket" method="post" class="form-container" onsubmit="return ticketAlerts()">
            </c:when>
        </c:choose>

        <%--@declare id="ticketAmount"--%><h2><fmt:message key="ticket_view.ticket_form"/></h2>

        <fmt:message key="ticket_view.your_balance"/>: ${sessionScope.balance}<br>
        <label for="ticketAmount"><b>
                <fmt:message key="ticket_view.tickets_amount"/>:
        </b></label>
                <fmt:message key="ticket_view.enter_amount" var="amount"/>
        <input style="width: 250px" type="number" name="ticketAmount" id="ticketAmount"
               value="1" placeholder="${amount}" min="1"
<%--                max="${requestScope.trip.getSeats()}"--%>
               onchange="showTotalPrice(this)" required><br>
        <div id="totalPrice"></div>
        <input type="hidden" name="tripId" value="${requestScope.trip.getId()}">
        <input type="hidden" name="tripCost" id="tripCost" value="${requestScope.trip.getCost()}">
        <input type="hidden" name="tripSeats" id="tripSeats" value="${requestScope.trip.getSeats()}">
        <input type="hidden" name="userBalance" id="userBalance" value="${sessionScope.balance}">

        <c:choose>
            <c:when test="${sessionScope.userLogin == null}">
                <button type="submit" class="btn" onclick="filterNotAuthorized()">
                    <fmt:message key="ticket_view.buy"/>
                </button>
            </c:when>
            <c:when test="${sessionScope.userLogin != null}">
                <button type="submit" class="btn"><fmt:message key="ticket_view.buy"/></button>
            </c:when>
        </c:choose>

        <button type="button" class="btn cancel" onclick="closeTicketAmountFormForm()">
            <fmt:message key="ticket_view.close"/>
        </button>
    </form>
</div>

<fmt:message key="menu.table.train" var="train"/>
<fmt:message key="menu.table.start" var="start"/>
<fmt:message key="menu.table.dep_date" var="dep_date"/>
<fmt:message key="menu.table.dep_time" var="dep_time"/>
<fmt:message key="menu.table.destination" var="end"/>
<fmt:message key="menu.table.arr_date" var="arr_date"/>
<fmt:message key="menu.table.arr_time" var="arr_time"/>
<fmt:message key="menu.table.duration" var="duration"/>
<fmt:message key="menu.table.seats" var="seats"/>
<fmt:message key="menu.table.cost" var="cost"/>

<table style="width: 100%; height: 50px">
    <tr style="height: 20px">
        <th>${train}</th>
        <th>${start}</th>
        <th>${dep_date}</th>
        <th>${dep_time}</th>
        <th>${end}</th>
        <th>${arr_date}</th>
        <th>${arr_time}</th>
        <th>${duration}</th>
        <th>${seats}</th>
        <th>${cost}</th>
    </tr>
        <tr style="height: 30px">
            <th>
                    ${requestScope.trip.getTrain()}
            </th>
            <th>${requestScope.trip.getStartStation()}</th>
            <th>${requestScope.trip.getDepartureDate()}</th>
            <th>${requestScope.trip.getDepartureTime()}</th>
            <th>${requestScope.trip.getFinalStation()}</th>
            <th>${requestScope.trip.getArrivalDate()}</th>
            <th>${requestScope.trip.getArrivalTime()}</th>
            <th>${requestScope.trip.getDuration()}</th>
            <th>${requestScope.trip.getSeats()}</th>
            <th>${requestScope.trip.getCost()}</th>
        </tr>
</table>
<br>
<br>
<h3 style="text-align: center"><fmt:message key="ticket_view_all_stations"/></h3>
<table style="width: 100%; height: 40%">
    <tr style="height: 20px">
            <th style="width: 350px"><fmt:message key="ticket_view_station"/></th>
            <th><fmt:message key="ticket_view_time"/></th>
    </tr>
<c:forEach items="${requestScope.allStation}" var="settlement">
    <tr>
        <th style="width: 350px">${settlement.getValue()}</th>
        <th>00:00:00</th>
    </tr>
</c:forEach>
</table>
</body>
</html>
