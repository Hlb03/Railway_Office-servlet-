<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>User basket</title>

    <style>
        table, th, td {
            border:1px solid black;
            height: 15px;
        }

        .welcome-message{
            border-radius: 15px;
            background-color: grey;
            text-align: center; //chocolate
        }

        .trips_location {
            position: relative;
            text-align: center;
        }

        .pagination center {
            text-align: center;
        }

        .pagination {
            display: inline-block;
        }

        .pagination a {
            color: black;
            float: left;
            padding: 8px 16px;
            text-decoration: none;
        }

        .pagination a.active {
            background-color: #4CAF50;
            color: white;
            border-radius: 5px;
        }

        .pagination a:hover:not(.active) {
            background-color: #ddd;
            border-radius: 5px;
            border-style: solid;
            border-color: grey;
        }
    </style>
</head>
<body>
<h1 class="welcome-message">${sessionScope.userNS} basket</h1>


<hr>
<br>

<div class="trips_location">
    <h3 style="text-align: center; color: grey; font-family: 'Bookman Old Style',serif">Your tickets: </h3>

    <table style="width: 100%; height: 50px">
        <tr style="height: 20px">
            <th>Train</th>
            <%--        <th>Id</th>--%> <!-- Not sure whether this column is needed -->
            <th>Start</th>
            <th>Departure date</th>
            <th>Departure time</th>
            <th>Destination</th>
            <th>Arrival date</th>
            <th>Arrival time</th>
            <th>Duration</th>
            <th>Ticket amount</th>
            <th>Cost (per one)</th>
        </tr>
        <c:forEach items="${requestScope.userTripsAmount}" var="trip">
            <tr style="height: 30px">
                <th><a
                        href="routeInfo?trip_id=${trip.getId()}&start=${trip.getStartStation()}&depart=${trip.getDepartureDate()} ${trip.getDepartureTime()}&destination=${trip.getFinalStation()}&buy=false">
                        ${trip.getTrain()}</a></th>
                    <%--            <th>${trip.getId()}</th>--%> <!-- Not sure whether this column is needed -->
                <th>${trip.getStartStation()}</th>
                <th>${trip.getDepartureDate()}</th>
                <th>${trip.getDepartureTime()}</th>
                <th>${trip.getFinalStation()}</th>
                <th>${trip.getArrivalDate()}</th>
                <th>${trip.getArrivalTime()}</th>
                <th>${trip.getDuration()}</th>
                <th>${trip.getSeats()}</th>
                <th>${trip.getCost()}</th>
            </tr>
        </c:forEach>
    </table>
</div>

<nav aria-label="Navigation for trips">
    <ul class="pagination">
        <c:if test="${requestScope.currentPage != 1}">
            <a class="page-link"
               href="userBasketInfo?recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage-1}">&laquo;</a>
        </c:if>

        <c:forEach begin="1" end="${requestScope.pagesAmount}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <a class="active">
                            ${i}
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="page-link"
                       href="userBasketInfo?recordsPerPage=${requestScope.recordsPerPage}&currentPage=${i}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${requestScope.currentPage lt requestScope.pagesAmount}">
            <a class="page-link"
               href="userBasketInfo?recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage+1}">&raquo;</a>
        </c:if>
    </ul>
</nav>

</body>
</html>