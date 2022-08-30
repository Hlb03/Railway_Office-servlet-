<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Add new trip</title>
    <script type="text/javascript">
        <%@include file="/WEB-INF/js/dynamic_generate_of_input_field.js"%>
        <%@include file="/WEB-INF/js/popup_settlement-create_form.js"%>
        <%@include file="/WEB-INF/js/popup_settlement-delete_form.js"%>
        <%@include file="/WEB-INF/js/popup_train-create_form.js"%>
        <%@include file="/WEB-INF/js/popup_train-delete_form.js"%>
        <%@include file="/WEB-INF/js/submit_adding_trip.js"%>
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

        /* Button used to open the contact form - fixed at the bottom of the page */
        .add_settlement {
            background-color: #555;
            color: white;
            padding: 16px 20px;
            border: none;
            cursor: pointer;
            opacity: 0.8;
            position: fixed;
            top: 30px;
            left: 100px;
            width: 200px;
        }

        .delete_settlement {
            background-color: #555;
            color: white;
            padding: 16px 20px;
            border: none;
            cursor: pointer;
            opacity: 0.8;
            position: fixed;
            top: 30px;
            left: 1320px;
            width: 200px;
        }

        /* Button used to open the contact form - fixed at the bottom of the page */
        .add_train-button {
            background-color: #555;
            color: white;
            padding: 16px 20px;
            border: none;
            cursor: pointer;
            opacity: 0.8;
            position: fixed;
            bottom: 23px;
            right: 28px;
            width: 200px;
        }

        .delete_train-button {
            background-color: #555;
            color: white;
            padding: 16px 20px;
            border: none;
            cursor: pointer;
            opacity: 0.8;
            position: fixed;
            bottom: 23px;
            right: 1320px;
            width: 200px;
        }

        .form-popup-position-settlement_create {
            display: none;
            position: fixed;
            top: 30px;
            left: 100px;
            border: 3px solid #f1f1f1;
            z-index: 9;
        }

        .form-popup-position-settlement_delete {
            display: none;
            position: fixed;
            top: 30px;
            left: 1280px;
            border: 3px solid #f1f1f1;
            z-index: 9;
        }

        /* The popup form - hidden by default */
        .form-popup-position-train_create {
            display: none;
            position: fixed;
            bottom: 0;
            right: 15px;
            border: 3px solid #f1f1f1;
            z-index: 9;
        }

        .form-popup-position-train_delete {
            display: none;
            position: fixed;
            bottom: 0;
            right: 1280px;
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

</head>
<body>

<button class="add_settlement" onclick="openSettlementCreateForm()">Add settlement</button>

<!-- Popup form for adding balance replenishment -->
<div class="form-popup-position-settlement_create" id="mySettlementCreateForm">
    <form action="addNewSettlement" method="post" class="form-container">
        <h2>New settlement</h2>

        <label for="createSettlementName"><b>Settlement name:</b></label>
        <input type="text" placeholder="Enter name" style="width: 250px"
               name="settlementName" id="createSettlementName" required>
        <button type="submit" class="btn">Add</button>
        <button type="button" class="btn cancel" onclick="closeSettlementCreateForm()">Close</button>
    </form>
</div>

<button class="delete_settlement" onclick="openSettlementDeleteForm()">Delete settlement</button>

<!-- Popup form for adding balance replenishment -->
<div class="form-popup-position-settlement_delete" id="mySettlementDeleteForm">
    <form action="deleteSettlement" method="post" class="form-container">
        <h2>Delete settlement</h2>

        <label for="deleteSettlementName"><b>Settlement name:</b></label>
        <input type="text" placeholder="Enter name" style="width: 250px"
               name="settlementName" id="deleteSettlementName" required>
        <button type="submit" class="btn">Delete</button>
        <button type="button" class="btn cancel" onclick="closeSettlementDeleteForm()">Close</button>
    </form>
</div>


<button class="delete_train-button" onclick="openDeleteTrainForm()">Add train</button>

<!-- Popup form for adding new train -->
<div class="form-popup-position-train_delete" id="myTrainDeleteForm">
    <form action="newTrain" method="post" class="form-container">
        <h1>New train</h1>

        <label for="trainNumb"><b>Train number:</b></label>
        <input type="text" placeholder="Enter value" style="width: 250px"
               name="trainNumber" id="trainNumb" pattern="[0-9]{3}[А-Я]" minlength="4" maxlength="4" required>
        <button type="submit" class="btn">Add</button>
        <button type="button" class="btn cancel" onclick="closeDeleteTrainForm()">Close</button>
    </form>
</div>

<button class="add_train-button" onclick="openCreateTrainForm()">Delete train</button>

<!-- Popup form for adding new train -->
<div class="form-popup-position-train_create" id="myTrainCreateForm">
    <form action="deleteTrain" method="post" class="form-container">
        <h1>Delete train</h1>

        <label for="trainNum"><b>Train number:</b></label>
        <input type="text" placeholder="Enter value" style="width: 250px"
               name="trainNumber" id="trainNum" pattern="[0-9]{3}[А-Я]" minlength="4" maxlength="4" required>
        <button type="submit" class="btn">Delete</button>
        <button type="button" class="btn cancel" onclick="closeCreateTrainForm()">Close</button>
    </form>
</div>


<div class="container">
    <h1  style="text-align: center">Form to add new trip</h1>
    <form name="registration" class="registartion-form" action="addTrip" method="post"> <!-- -->
        <table>
            <tr>
                <td><label for="depDate">Departure date:</label></td>
                <td ><input style="width: 170px" type="date" name="depDate" id="depDate" required></td>
            </tr>
            <tr>
                <td><label for="depTime">Departure time:</label></td>
                <td><input style="width: 170px" type="time" name="depTime" step="1" id="depTime" required></td>
            </tr>
            <tr>
                <td><label for="arrDate">Arrival date:</label></td>
                <td><input style="width: 170px" type="date" name="arrDate" id="arrDate" required></td>
            </tr>
            <tr>
                <td><label for="arrTime">Arrival time:</label></td>
                <td><input style="width: 170px;" type="time" name="arrTime" step="1" id="arrTime" required></td>
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
            <div id="settlementAmount"></div>
            <!-- Add here a confirm js method if not present -->
            <td colspan="2"><input style="text-align: center" type="submit" class="submit" onclick="return submitAddingTrip()" value="Add new trip" /></td>
        </table>
    </form>
</div>
</body>

</html>