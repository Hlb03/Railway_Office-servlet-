<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Main menu</title>
<%--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"--%>
<%--          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"--%>
<%--          crossorigin="anonymous">--%>

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

        .search_form {
            text-align: center;
            color: grey;
            border: blue;
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
<h1 class="welcome-message">Railway Office</h1>

<!-- Choose the suitable heading -->
<c:choose>
    <c:when test="${sessionScope.userLog == null}">
        <%@ include file="../jspf/heading_not_authorized.jspf" %>
    </c:when>
    <c:when test="${sessionScope.userLog != null}">
        <%@ include file="../jspf/heading_authorized.jspf" %>
    </c:when>
</c:choose>


<!-- Includes button that can add new train/trip -->
<c:if test="${sessionScope.userRole eq 'admin'}">
    <%@ include file="../jspf/admin_rights_for_menu.jspf" %>
</c:if>

<!-- Try to put allTrips into session scope -->
<%--<h3 style="text-align: center; color: green">${requestScope.messageInfo}</h3> <!-- Message from adding train -->--%>

<hr>
<!-- Form for choosing amount of data on page -->
<form> <!-- action="ReadCountries" -->

    <input type="hidden" name="currentPage" value="1">

    <div class="form-group col-md-4">

        <label for="records">Select records per page:</label>

        <select class="form-control" id="records" name="recordsPerPage">
            <option value="4">4</option>
            <option value="5" selected>5</option>
            <option value="6">6</option>
            <option value="7">7</option>
        </select>

    </div>

<%--    <button type="submit" class="btn btn-primary">Submit</button>--%>

</form>
<hr>
<h3 style="color: black; text-align: center">Search for route:</h3>
<div class="search_form">
<form autocomplete="on" action="searchTrip">
    <label for="startPoint"></label><input type="search" name="from"
                                           placeholder="Start" id="startPoint" required>
    <label for="arrivalPoint"></label><input type="search" name="to"
                                             placeholder="Destination" id="arrivalPoint" required>
    <label>
        <input type="date" placeholder="Date" name="date" value="Date">
    </label>
    <input type="submit" value="Search">
</form>
</div>
<br>

<div class="trips_location">
<h3 style="text-align: center; color: grey; font-family: 'Bookman Old Style',serif">All available trips: </h3>

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
        <th>Seats</th>
        <th>Cost</th>
    </tr>
    <c:forEach items="${requestScope.allTrips}" var="trip">
        <tr style="height: 30px">
            <th><a
                    href="routeInfo?trip_id=${trip.getId()}&start=${trip.getStartStation()}&depart=${trip.getDepartureDate()} ${trip.getDepartureTime()}&destination=${trip.getFinalStation()}">
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
                            href="menu?recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage-1}">&laquo;</a>
            <!-- Previous -->
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
                         href="menu?recordsPerPage=${requestScope.recordsPerPage}&currentPage=${i}">${i}</a> <!-- ¤tPage -->
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${requestScope.currentPage lt requestScope.pagesAmount}">
                <a class="page-link"
                         href="menu?recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage+1}">&raquo;</a>
            <!-- Next -->
        </c:if>
    </ul>
</nav>

</body>
</html>