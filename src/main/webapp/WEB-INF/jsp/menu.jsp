<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message"/>

<html lang="${cookie['lang'].value}">
<head>
    <title>Main menu</title>

    <style>
        table, th, td {
            border: 1px solid black;
            height: 15px;
        }

        .welcome-message {
            border-radius: 15px;
            background-color: grey;
            text-align: center;
        / / chocolate
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
<%--<h1 class="welcome-message">Railway Office</h1>--%>
<h1 class="welcome-message"><fmt:message key="menu.main.header"/></h1>
<!-- Choose the suitable heading -->
<c:choose>
    <c:when test="${sessionScope.userLogin == null}">
        <%@ include file="../jspf/heading_not_authorized.jspf" %>
    </c:when>
    <c:when test="${sessionScope.userLogin != null}">
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


<fmt:message key="menu.main.search_start" var="startPoint"/>
<fmt:message key="menu.main.search_end" var="endPoint"/>
<fmt:message key="menu.main.search_for_route_button" var="search"/>

<hr>

<h3 style="color: black; text-align: center"><fmt:message key="menu.main.search_for_route">:</h3>
<div class="search_form">
    <form autocomplete="on" action="searchTrip">
        <label for="startPoint"></label><input type="search" name="from"
                                               placeholder="${startPoint}" id="startPoint" required>
        <label for="arrivalPoint"></label><input type="search" name="to"
                                                 placeholder="${endPoint}" id="arrivalPoint" required>
        <label>
            <input type="date" placeholder="Date" name="date" value="Date">
        </label>
        <input type="submit" value="${search}">
    </form>
</div>
<br>


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

<c:choose>
    <c:when test="${requestScope.tripsFromSearch.isEmpty()}">
        <div class="trips_location">
            <h3 style="text-align: center; color: brown; font-family: 'Bookman Old Style',serif">
                <fmt:message key="menu.message.trips_not_fount"/>
            </h3>
        </div>
    </c:when>
    <c:when test="${requestScope.tripsFromSearch != null}">
        <div class="trips_location">
            <h3 style="text-align: center; color: grey; font-family: 'Bookman Old Style',serif">
                <fmt:message key="menu.message.trips_were_fount"/>: </h3>

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
                <c:forEach items="${requestScope.tripsFromSearch}" var="trip">
                    <tr style="height: 30px">
                        <th><a
                                href="routeInfo?trip_id=${trip.getId()}&start=${trip.getStartStation()}&depart=${trip.getDepartureDate()}
                                ${trip.getDepartureTime()}&destination=${trip.getFinalStation()}">
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

        <nav aria-label="Navigation for trips">
            <ul class="pagination">
                <c:if test="${requestScope.currentPage != 1}">
                    <a class="page-link"
                       href="searchTrip?from=${requestScope.from}&to=${requestScope.to}&date=${requestScope.date}&
                                    recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage-1}">&laquo;</a>
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
                               href="searchTrip?from=${requestScope.from}&to=${requestScope.to}&date=${requestScope.date}
                                                      &recordsPerPage=${requestScope.recordsPerPage}&currentPage=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${requestScope.currentPage lt requestScope.pagesAmount}">
                    <a class="page-link"
                       href="searchTrip?from=${requestScope.from}&to=${requestScope.to}&date=${requestScope.date}&
                                    recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage+1}">&raquo;</a>
                </c:if>
            </ul>
        </nav>
    </c:when>


    <c:when test="${requestScope.tripsFromSearch == null}">
        <div class="trips_location">
            <h3 style="text-align: center; color: grey; font-family: 'Bookman Old Style',serif">
                <fmt:message key="menu.message.all_trips"/>: </h3>

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
                <c:forEach items="${requestScope.allTrips}" var="trip">
                    <tr style="height: 30px">
                        <th><a
                                href="routeInfo?trip_id=${trip.getId()}&start=${trip.getStartStation()}&depart=${trip.getDepartureDate()} ${trip.getDepartureTime()}&destination=${trip.getFinalStation()}">
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

        <nav aria-label="Navigation for trips">
            <ul class="pagination">
                <c:if test="${requestScope.currentPage != 1}">
                    <a class="page-link"
                       href="menu?recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage-1}">&laquo;</a>
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
                               href="menu?recordsPerPage=${requestScope.recordsPerPage}&currentPage=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${requestScope.currentPage lt requestScope.pagesAmount}">
                    <a class="page-link"
                       href="menu?recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage+1}">&raquo;</a>
                </c:if>
            </ul>
        </nav>
    </c:when>
</c:choose>

</body>
</html>