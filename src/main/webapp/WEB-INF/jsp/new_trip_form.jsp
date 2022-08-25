<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Add new trip</title>
    <script type="text/javascript">
        <%@include file="/WEB-INF/js/dynamic_generate_of_input_field.js"%>
<%--        <%@include file="/WEB-INF/js/submit_adding_trip.js"%>--%>
    </script>

    <style>
        * {
            margin: 0
        }

        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            height: 100vh;
            background-color: #6699cc;
        }

        .container h1 {
            color: white;
            font-family: sans-serif;
            margin: 20px;
        }

        .registartion-form {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 600px;
            color: rgb(255, 255, 255);
            font-size: 18px;
            font-family: sans-serif;
            background-color: #154a68;
            padding: 20px;
        }

        .registartion-form input,
        .registartion-form select,
        .registartion-form textarea {
            border: none;
            padding: 5px;
            margin-top: 10px;
            font-family: sans-serif;
        }

        .registartion-form input:focus,
        .registartion-form textarea:focus {
            box-shadow: 3px 3px 10px rgb(228, 228, 228), -3px -3px 10px rgb(224, 224, 224);
        }

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

<div class="container">
    <h1  style="text-align: center">Form to add new trip</h1>
    <form name="registration" class="registartion-form" action="addTrip">
        <table>
            <tr>
                <td><label for="depDate">Departure date:</label></td>
                <td ><input style="width: 170px" type="date" name="depDate" id="depDate" required></td>
            </tr>
            <tr>
                <td><label for="depTime">Departure time:</label></td>
                <td><input style="width: 170px" type="time" name="depTime" id="depTime" required></td>
            </tr>
            <tr>
                <td><label for="arrDate">Arrival date:</label></td>
                <td><input style="width: 170px" type="date" name="arrDate" id="arrDate" required></td>
            </tr>
            <tr>
                <td><label for="arrTime">Arrival time:</label></td>
                <td><input style="width: 170px;" type="time" name="arrTime" id="arrTime" required></td>
            </tr>
            <tr>
                <td><label for="seatsAmount">Seats amount:</label></td>
                <td><input style="width: 170px;" type="number" min="20" name="seats" id="seatsAmount" required></td>
            </tr>
            <tr>
                <td><label for="priceAmount">Price:</label></td>
                <td><input style="width: 170px;" type="number" min="180" step="0.01" name="price" id="priceAmount" required></td>
            </tr>
            <tr>
                <td><label for="trainNumber">Train:</label></td>
                <td>
                    <select style="width: 180px" name="train" id="trainNumber" required>
                        <c:forEach items="${requestScope.allTrains}" var="train">
                            <option value="${train.getId()}">${train.getNumber()}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="stations">Number of stations:</label></td>
                <td>
                    <input style="width: 170px" type="number" min="2" max="10" value="0" name="stationsAmount"
                           id="stations" onchange="getNewStationsInput(this)">
                </td>
            </tr>
            <div id="roomtypes"></div>
            <td colspan="2"><input style="text-align: center" type="submit" class="submit" value="Add new trip" /></td>
        </table>
    </form>
</div>

</body>

</html>
<title></title>