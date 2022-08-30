<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dynamic Fields - Day #29</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <!-- include font awesome -->
    <link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <script type="text/javascript">
        <%@include file="/WEB-INF/js/dynamic_input_fields_for_edit.js"%>
        <%@include file="/WEB-INF/js/submit_changes_on_ticket.js"%>
    </script>

    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jost:wght@100;300;400;700&display=swap');

        table, th, td {
          border:1px solid black;
        }

        body {
            background-color: #f0f5ff;
            font-family: 'Jost',sans-serif;
            /*color: #fff;*/
        }

        .wrapper {
            width: 400px;
            margin: 40px auto;
            padding: 10px;
            border-radius: 5px;
            background: white;
            box-shadow: 0px 10px 40px 0px rgba(47,47,47,.1);
        }

        /*[type=""]*/
        input {
            padding: 10px;
            margin: 10px auto;
            display: block;
            border-radius: 5px;
            border: 1px solid lightgrey;
            background: none;
            width: 274px;
            color: black;
        }

        /*[type="text"]*/
        input:focus {
            outline: none;
        }

        .controls {
            width: 294px;
            margin: 15px auto;
        }

        #remove_fields {
            float: right;
        }
        .controls a i.fa-minus {
            margin-right: 5px;
        }

        a {
            color: black;
            text-decoration: none;
        }

        h1 {
            text-align: center;
            /*font-size: 48px;*/
            color: #232c3d;
        }

        /*css for submit button button*/
        .registartion-form .submit {
            width: 100%;
            padding: 8px 0;
            font-size: 20px;
            color: rgb(44, 44, 44);
            background-color: #ffffff;
            border-radius: 5px;
        }
        .registartion-form .submit:hover {
            box-shadow: 3px 3px 6px rgb(255, 214, 176);
        }
            </style>
        </head>
        <body>

        <h1>Edit trip</h1>

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
                <th>${requestScope.trip.getTrain()}</th>
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

        <h3 style="text-align: center">All stations:</h3>
        <table style="width: 100%; height: 20%">
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

        <div class="wrapper">
            <form action="editCurrentTrip" method="post">
            <div id="survey_options">
                <h4 style="text-align: center">Change data</h4>
                <label> Departure date:
                    <input type="date" name="depDate" class="survey_options" size="50"
                           value="${requestScope.trip.getDepartureDate()}">
                </label>
                <label> Departure time:
                    <input type="time" name="depTime" class="survey_options" size="50" step="1"
                           value="${requestScope.trip.getDepartureTime()}">
                </label>
                <label> Arrival date:
                <input type="date" name="arrDate" class="survey_options" size="50"
                       value="${requestScope.trip.getArrivalDate()}">
                </label>
                <label> Arrival time:
                    <input type="time" name="arrTime" class="survey_options" size="50" step="1"
                           value="${requestScope.trip.getArrivalTime()}">
                </label>
                <input type="hidden" name="tripId" value="${requestScope.trip.getId()}">
                <label> Seats amount:
                    <input type="number" name="seats" class="survey_options" size="50" min="20"
                           value="${requestScope.trip.getSeats()}">
                </label>
                <label> Price amount:
                    <input type="number" name="price" class="survey_options" size="50" min="180" step="0.01"
                           value="${requestScope.trip.getCost()}">
                </label>
                <label> Train number: <br>
                    <select name="train" class="survey_options"
                            style="width: 275px; height: 30px; right: 30px">
                        <c:forEach items="${requestScope.allTrains}" var="train">
                                <c:choose>
                                    <c:when test="${requestScope.trip.getTrain() eq train.getNumber()}">
                                        <option value="${train.getNumber()}" selected>${train.getNumber()}</option>
                                    </c:when>
                                    <c:when test="${requestScope.trip.getTrain() ne train.getNumber()}">
                                        <option value="${train.getNumber()}">${train.getNumber()}</option> <!-- value="train.getId()" -->
                                    </c:when>
                                </c:choose>
                        </c:forEach>
                    </select>
                </label> <br>
                <label> Stations amount:
                    <input type="number" class="survey_options" min="2" max="10"
                            value="${requestScope.allStation.size()}" name="stationsAmount"
                           id="stations" onchange="return newFields(this)">
                </label>
                <div id="elements"></div>
                <div class="registartion-form">
                    <input class="submit" type="submit" style="text-align: center" value="Save" onclick="return submitChanges()">
                </div>
            </div>
            </form>
        </div>

</body>
</html>