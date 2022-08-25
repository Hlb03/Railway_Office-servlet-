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
    </style>
</head>
<body>
<h1 class="welcome-message">Detailed info</h1>

<h3 style="text-align: center">Detailed info about route: ${requestScope.route}</h3>

<c:if test="${sessionScope.userRole eq 'admin'}">
    <%@ include file="../jspf/admin_rights_for_ticket.jspf" %>
</c:if>

<form style="text-align: right" action="buyTicket"> <!-- style="text-align: right" -->
    <button  onclick="return buyTicket()">Buy ticket</button>
</form>

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
