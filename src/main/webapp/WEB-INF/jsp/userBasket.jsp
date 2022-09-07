<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message"/>

<html>
<head>
    <title><fmt:message key="user_basket.title"/></title>

    <style>
        <%@include file="/WEB-INF/css/locale_position.css"%>

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
<h1 class="welcome-message"><fmt:message key="user_basket.heading"/> ${sessionScope.userNS}</h1>

<div class="lang_location">
    <a href="userBasketInfo?&cookieLocale=en">EN</a> |
    <a href="userBasketInfo?cookieLocale=uk">UK</a>
</div>
<hr>
<br>
<c:choose>
    <c:when test="${!requestScope.userBoughtTrips.isEmpty()}">
        <div class="trips_location">
            <h3 style="text-align: center; color: grey; font-family: 'Bookman Old Style',serif"><fmt:message key="user_basket.tickets"/>: </h3>

            <fmt:message key="menu.table.train" var="train"/>
            <fmt:message key="menu.table.start" var="start"/>
            <fmt:message key="menu.table.dep_date" var="dep_date"/>
            <fmt:message key="menu.table.dep_time" var="dep_time"/>
            <fmt:message key="menu.table.destination" var="end"/>
            <fmt:message key="menu.table.arr_date" var="arr_date"/>
            <fmt:message key="menu.table.arr_time" var="arr_time"/>
            <fmt:message key="menu.table.duration" var="duration"/>

            <fmt:message key="user_basket.tickets_amount" var="amount"/>
            <fmt:message key="user_basket.cost" var="cost"/>

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
                    <th>${amount}</th>
                    <th>${cost}</th>
                </tr>
                <c:forEach items="${requestScope.userBoughtTrips}" var="trip">
                    <tr style="height: 30px">
                        <th><a
                        href="routeInfo?trip_id=${trip.getId()}&start=${trip.getStartStation()}&depart=${trip.getDepartureDate()} ${trip.getDepartureTime()}&destination=${trip.getFinalStation()}&buy=false">
                        ${trip.getTrain()}</a></th>
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
    </c:when>
    <c:when test="${requestScope.userBoughtTrips.isEmpty()}">
        <h2 style="text-align: center; color: red"><fmt:message key="user_basket.message"/></h2>
    </c:when>
</c:choose>

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