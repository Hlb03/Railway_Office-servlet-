<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript">
        <%@include file="/WEB-INF/js/buy_message.js"%>
    </script>

    <title>Ticket view</title>
    <style>
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
<h1 class="welcome-message">Detailed info</h1>

<!-- Create over here a custom tag -->
<h3 style="text-align: center">Detailed info about route: ${requestScope.route}</h3>

<c:if test="${sessionScope.userRole eq 'admin'}">
    <%@ include file="../jspf/admin_rights_for_ticket.jspf" %>
</c:if>

<c:if test="${requestScope.buyOpportunity == null}">
    <button class="ticket_amount-button" onclick="openTicketAmountForm()">Buy ticket</button>
</c:if>

<%--    <button class="ticket_amount-button"  onclick="openTicketAmountForm()">Buy ticket</button>--%>

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

        <%--@declare id="ticketAmount"--%><h2>Ticket form</h2>

        Your balance: ${sessionScope.balance}<br>
        <label for="ticketAmount"><b>Amount of ticket you want to buy:</b></label>
        <input style="width: 250px" type="number" name="ticketAmount" id="ticketAmount"
               value="1" placeholder="Enter amount" min="1" max="${requestScope.trip.getSeats()}"
               onchange="showTotalPrice(this)" required><br>
        <div id="totalPrice"></div>
        <input type="hidden" name="tripId" value="${requestScope.trip.getId()}">
        <input type="hidden" name="tripCost" id="tripCost" value="${requestScope.trip.getCost()}">
        <input type="hidden" name="tripSeats" id="tripSeats" value="${requestScope.trip.getSeats()}">
        <input type="hidden" name="userBalance" id="userBalance" value="${sessionScope.balance}">

        <c:choose>
            <c:when test="${sessionScope.userLogin == null}">
                <button type="submit" class="btn" onclick="filterNotAuthorized()">Buy</button>
            </c:when>
            <c:when test="${sessionScope.userLogin != null}">
                <button type="submit" class="btn">Buy</button>
            </c:when>
        </c:choose>

        <button type="button" class="btn cancel" onclick="closeTicketAmountFormForm()">Close</button>
    </form>
</div>

<table style="width: 100%; height: 50px">
    <tr style="height: 20px">
        <th>Train</th>
        <th>Start</th>
        <th>Departure date</th>
        <th>Departure time</th>
        <th>Destination</th>
        <th>Arrival date</th>
        <th>Arrival time</th>
        <th>Duration</th>
        <th>Seats</th>
        <th>Cost</th>
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
<h3 style="text-align: center">All stations:</h3>
<table style="width: 100%; height: 40%">
    <tr style="height: 20px">
            <th style="width: 350px">Station</th>
            <th>Time arrival</th>
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
