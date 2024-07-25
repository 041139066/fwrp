<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subscription Form</title>
    <link rel="stylesheet" href="resources/css/base.css">
    <style>
        /* Navbar */
        nav {
            background-color: #2c3e50;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            position: sticky;
            top: 0;
            z-index: 1000;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
        }

        .nav-left div, .nav-right div {
            margin: 0 15px;
        }

        nav a {
            color: #fff;
            text-decoration: none;
            font-weight: 600;
        }

        nav a:hover {
            text-decoration: underline;
        }

        .nav-left {
            display: flex;
        }

        .nav-right {
            margin-right: 15px;
        }

        /* Main Container */
        .container {
            flex: 1;
            width: 100%;
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background: rgba(255, 255, 255, 0.3); /* Semi-transparent white */
            backdrop-filter: blur(10px); /* Glass effect */
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        }

        /* Table */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background: rgba(255, 255, 255, 0.5); /* Semi-transparent white */
            backdrop-filter: blur(10px); /* Glass effect */
            border-radius: 12px;
            overflow: hidden; /* For rounded corners */
        }

        th, td {
            padding: 12px 15px;
            border: 1px solid #ddd;
        }

        th {
            background-color: #f4f4f4;
            color: #2c3e50;
        }

        td:last-child {
            text-align: center;
        }

        tr:nth-child(even) {
            background-color: rgba(255, 255, 255, 0.3);
        }

        /* Buttons */
        .button {
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 5px;
            transition: background 0.3s ease;
        }

        .button-add {
            background-color: #3498db;
            color: white;
        }

        .button-add:hover {
            background-color: #2980b9;
        }

        .button-edit {
            background-color: #f39c12;
            color: white;
        }

        .button-edit:hover {
            background-color: #e67e22;
        }

        .button-delete {
            background-color: #e74c3c;
            color: white;
        }

        .button-delete:hover {
            background-color: #c0392b;
        }

        .button-logout {
            background-color: #2ecc71;
            color: white;
        }

        .button-logout:hover {
            background-color: #27ae60;
        }

        /* Form Elements */
        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        label {
            font-weight: bold;
        }

        select, input[type="text"], input[type="email"], input[type="tel"] {
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ddd;
            width: 100%;
            max-width: 400px;
        }

        input[type="checkbox"] {
            margin-right: 10px;
        }

        .food-preferences {
            max-height: 200px;
            overflow-y: auto;
            margin-bottom: 15px;
        }

        .food-preferences table {
            width: 100%;
            border-collapse: collapse;
            background: rgba(255, 255, 255, 0.5); /* Semi-transparent white */
        }

        .food-preferences th, .food-preferences td {
            padding: 10px;
            border: 1px solid #ddd;
        }

        .food-preferences th {
            background-color: #f4f4f4;
            color: #2c3e50;
        }

        .food-preferences tr:nth-child(even) {
            background-color: rgba(255, 255, 255, 0.3);
        }

        .food-preferences input[type="checkbox"] {
            margin-right: 10px;
        }

        /* Footer */
        footer {
            text-align: center;
            padding: 15px;
            background-color: #2c3e50;
            color: #fff;
            position: relative;
            bottom: 0;
            width: 100%;
            box-shadow: 0 -4px 8px rgba(0, 0, 0, 0.2);
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<!-- Navigation Bar -->
<nav>
    <ul>
        <li><a href="purchase.jsp">Purchase</a></li>
        <li><a href="logout.jsp">Logout</a></li>
    </ul>
</nav>

<!-- Container -->
<div class="container">
    <h2>Subscription Form</h2>
    <form id="subscriptionForm" method="post" action="submitSubscription">
        <!-- Location Selection -->
        <label for="province">Province:</label>
        <select id="province" name="province">
            <option value="">Select Province</option>
            <c:forEach var="province" items="${provinces}">
                <option value="${province.id}">${province.province}</option>
            </c:forEach>
        </select>

        <label for="city">City:</label>
        <select id="city" name="city">
            <option value="">Select City</option>
            <!-- Options will be dynamically loaded based on selected province -->
        </select>

        <!-- Subscription Method -->
        <label for="method">Subscription Method:</label>
        <select id="method" name="method">
            <option value="">Select Method</option>
            <option value="email">Email</option>
            <option value="sms">SMS</option>
        </select>

        <div id="contactInfo" style="display: none;">
            <label for="contact">Contact:</label>
            <input type="text" id="contact" name="contact">
        </div>

        <!-- Food Preferences -->
        <label for="foodPreferences">Food Preferences:</label>
        <select id="foodPreferences" name="foodPreferences" multiple>
            <c:forEach var="food" items="${foodInventoryList}">
                <option value="${food.id}" <c:if test="${fn:contains(foodPreferences, food.id)}">selected</c:if>>${food.description}</option>
            </c:forEach>
        </select>

        <!-- Food Inventory Table -->
        <h3>Food Inventory</h3>
        <table>
            <thead>
            <tr>
                <th>Select</th>
                <th>Food Name</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="food" items="${foodInventoryList}">
                <tr>
                    <td><input type="checkbox" name="foodInventory" value="${food.id}"></td>
                    <td>${food.description}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Subscription Status Buttons -->
        <div id="subscriptionStatus">
            <c:choose>
                <c:when test="${subscription == null}">
                    <button type="submit" name="action" value="subscribe">Subscribe</button>
                </c:when>
                <c:when test="${subscription.status == false}">
                    <button type="submit" name="action" value="reactivate">Reactivate</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" name="action" value="update">Update</button>
                </c:otherwise>
            </c:choose>
        </div>
    </form>
</div>

<!-- Footer -->
<footer>
    &copy; 2024 Food Waste Reduction Platform. All rights reserved.
</footer>

<script>
    const provinceSelect = $('#province');
    const citySelect = $('#city');
    const cities = ${requestScope.cities};
    console.log(cities);
    let filteredCities;

    function filterCityList(provinceId) {
        citySelect.empty();
        citySelect.append('<option value="">Select City</option>');
        if (provinceId) {
            filteredCities = cities.filter(city => city.provinceId === provinceId);
            filteredCities.forEach(city =>
                citySelect.append('<option value="' + city.city + '">' + city.city + '</option>')
            );
        } else {
            filteredCities = cities;
            filteredCities.forEach(city => {
                citySelect.append('<option value="' + city.city + '">' + city.city + '</option>');
            });
        }
    }

    $(document).ready(function () {
        filterCityList();

        // Load cities based on selected province
        provinceSelect.change(function (val) {
            const provinceId = $(this).val();
            filterCityList(provinceId);
        });
        // Load cities based on selected province
        citySelect.change(function (val) {
            const city = filteredCities.find(city => city.city === $(this).val());
            if (city && city.provinceId !== provinceSelect.val()) {
                provinceSelect.val(city.provinceId);
            }
        });

        // Show contact input based on selected method
        $('#method').change(function() {
            var method = $(this).val();
            if (method === 'email') {
                $('#contact').attr('type', 'email');
                $('#contactInfo').show();
            } else if (method === 'sms') {
                $('#contact').attr('type', 'tel');
                $('#contactInfo').show();
            } else {
                $('#contactInfo').hide();
            }
        });
    });



</script>
</body>
</html>
