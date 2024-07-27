<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subscription Form</title>
    <link rel="stylesheet" href="resources/css/subscription.css">
    <style>
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<!-- Navigation Bar -->
<nav>
    <div class="nav-left">
        <div class="link"><a href="food_inventory.jsp">Purchase Food</a></div>
    </div>
    <div class="nav-right">
        <div class="link"><a href="logout.jsp">Log Out</a></div>
    </div>
</nav>

<!-- Container -->
<div class="container card">
    <h2>Subscription Form</h2>
    <form id="subscriptionForm" method="post" action="submitSubscription">
        <div class="form-buttons">
            <c:choose>
                <c:when test="${requestScope.subscription == null}">
                    <button type="submit" class="button-primary">Subscribe</button>
                </c:when>
                <c:when test="${requestScope.subscription.status == false}">
                    <button type="submit" class="button-primary">Reactivate</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="button-primary">Update</button>
                </c:otherwise>
            </c:choose>
        </div>
        <!-- Location Selection -->
        <div class="row">
            <div class="form-field">
                <label for="province">Province:</label>
                <select id="province" name="province">
                    <option value="">Select Province</option>
                    <c:forEach var="province" items="${requestScope.provinces}">
                        <option value="${province.id}">${province.province}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-field">
                <label for="city">City:</label>
                <select id="city" name="city">
                    <option value="">Select City</option>
                    <!-- Options will be dynamically loaded based on selected province -->
                </select>
            </div>

        </div>
        <div class="row">
            <!-- Subscription Method -->
            <div class="form-field">
                <label for="method">Subscription Method:</label>
                <select id="method" name="method">
                    <option value="">Select Method</option>
                    <option value="email">Email</option>
                    <option value="sms">SMS</option>
                </select>
            </div>
            <div>
                <div id="email-field" class="form-field" style="display: none;">
                    <label for="email">Email Address:</label>
                    <input type="text" id="email" name="email">
                </div>
                <div id="phone-field" class="form-field" style="display: none;">
                    <label for="phone">Phone Number:</label>
                    <input type="text" id="phone" name="contact">
                </div>
            </div>
        </div>
        <!-- Food Preferences -->
        <div class="form-field">
            <label for="foodPreferences">Food Preferences:</label>
            <textarea id="foodPreferences" disabled></textarea>
        </div>
        <table>
            <thead>
            <tr>
                <th>Select</th>
                <th>Food Name</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="food" items="${requestScope.foodInventoryList}">
                <tr>
                    <td style="text-align: center">
                        <input
                            type="checkbox"
                            name="foodInventory"
                            value="${food.id}"
                            <c:if test="${fn:contains(requestScope.foodPreferences, food.toJson())}">checked</c:if>
                            onchange="handleCheckboxChange(this, '${fn:escapeXml(food.toJson())}')"
                        />
                    </td>
                    <td>${food.description}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
</div>

<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>

<script>
    let foodPreferences = ${requestScope.foodPreferences};
    const cities = ${requestScope.cities};
    let filteredCities;

    const provinceSelect = $('#province');
    const citySelect = $('#city');
    const methodSelect = $('#method');
    const foodPreferencesSelect = $('#foodPreferences');

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

    function handleMethodChange() {
        const method = methodSelect.val();
        const email = $('#email-field');
        const phone = $('#phone-field');
        if (method === 'email') {
            phone.fadeOut(300, function () {
                email.fadeIn(300, function () {
                    phone.hide();
                    email.show();
                });
            });
        } else if (method === 'sms') {
            email.fadeOut(300, function () {
                phone.fadeIn(300, function () {
                    email.hide();
                    phone.show();
                });
            });
        } else {
            email.fadeOut(300, function () {
                email.hide();
            });
            phone.fadeOut(300, function () {
                phone.hide()
            });
        }
    }

    function updateFoodPreferences() {
        const strFoodPreferences = foodPreferences.map(itm => itm.description).join(", ");
        foodPreferencesSelect.val(strFoodPreferences);
    }

    function handleCheckboxChange(checkbox, jsonFood) {
        const isChecked = checkbox.checked;
        const food = JSON.parse(jsonFood);
        if(isChecked){
            foodPreferences.push(food);
        } else {
            foodPreferences = foodPreferences.filter(itm=>itm.id !== food.id);
        }
        updateFoodPreferences();
    }

    $(document).ready(function () {
        filterCityList();
        handleMethodChange();
        updateFoodPreferences();
        // Load cities based on selected province
        provinceSelect.change(function () {
            const provinceId = $(this).val();
            filterCityList(provinceId);
        });
        // Load cities based on selected province
        citySelect.change(function () {
            const city = filteredCities.find(city => city.city === $(this).val());
            if (city && city.provinceId !== provinceSelect.val()) {
                provinceSelect.val(city.provinceId);
            }
        });

        // Show contact input based on selected method
        $('#method').change(handleMethodChange);
    });


</script>
</body>
</html>
