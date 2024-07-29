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
    <c:choose>
        <%--        <c:when test="${requestScope.subscription == null}">--%>
        <%--            <div class="information-card" onClick="form.show()">--%>
        <%--                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">--%>
        <%--                    <path d="M12 5V19M5 12H19" stroke="black" stroke-width="2" stroke-linecap="round"--%>
        <%--                          stroke-linejoin="round"></path>--%>
        <%--                </svg>--%>
        <%--                <div>Add</div>--%>
        <%--            </div>--%>
        <%--        </c:when>--%>
        <c:when test="${requestScope.subscription.status == false}">
            <button class="button-primary info-button" onclick="handleReactivate()">Reactivate</button>
            <!-- Information Card -->
            <div class="info-card" style="display: none">
                <h3>Subscription Information</h3>
                <p><strong>Province:</strong> <span id="info-province"></span></p>
                <p><strong>City:</strong> <span id="info-city"></span></p>
                <p><strong>Communication Method:</strong> <span id="info-method"></span></p>
                <p style="display:none"><strong>Email:</strong> <span id="info-email"></span></p>
                <p style="display:none"><strong>Phone:</strong> <span id="info-phone"></span></p>
                <p><strong>Food Preferences:</strong> <span id="info-food-preferences"></span></p>
            </div>

        </c:when>
        <c:otherwise>
            <h2>Subscription Form</h2>
            <form id="subscriptionForm">
                <div class="form-buttons">
                    <c:if test="${requestScope.subscription.status == true}">
                        <button class="button-primary" type="button" onclick="handleUpdate()">Update</button>
                        <button class="button-primary" type="button" onclick="handleDeactivate()">Deactivate</button>
                    </c:if>
                    <c:if test="${requestScope.subscription == null}">
                        <button class="button-primary" type="button" onclick="handleSubscribe()">Subscribe</button>
                    </c:if>

                </div>
                <!-- Location Selection -->
                <div class="row">
                    <div class="form-field">
                        <label for="province">Province:</label>
                        <select id="province" name="province" onchange="handleProvinceChange()">
                            <option value="">Select Province</option>
                            <c:forEach var="province" items="${requestScope.provinces}">
                                <option value="${province.id}">${province.province}</option>
                            </c:forEach>
                        </select>
                        <span class="error-message" style="display: none">Please select a province.</span>
                    </div>
                    <div class="form-field">
                        <label for="city">City:</label>
                        <select id="city" name="city" onchange="handleCityChange()">
                            <option value="">Select City</option>
                            <!-- Options will be dynamically loaded based on selected province -->
                        </select>
                        <span class="error-message" style="display: none">Please select a city.</span>
                    </div>
                </div>
                <div class="row">
                    <!-- Subscription Method -->
                    <div class="form-field">
                        <label for="method">Subscription Method:</label>
                        <select id="method" name="method" onchange="handleMethodChange(); validateMethodField();">
                            <option value="">Select Method</option>
                            <option value="email">Email</option>
                            <option value="sms">SMS</option>
                        </select>
                        <span class="error-message"
                              style="display: none">Please select a communication method.</span>
                    </div>
                    <div>
                        <div id="email-field" class="form-field" style="display: none;">
                            <label for="email">Email Address:</label>
                            <input type="email" id="email" name="email" onchange="validateEmailField()">
                            <span class="error-message"
                                  style="display: none">Please enter a valid email address: "xxx@xxx.xxx".</span>
                        </div>
                        <div id="phone-field" class="form-field" style="display: none;">
                            <label for="phone">Phone Number:</label>
                            <input type="tel" id="phone" name="phone" onchange="validatePhoneField()">
                            <span class="error-message"
                                  style="display: none">Please enter a valid phone number: "xxxxxxxxxx", "xxx-xxx-xxxx" or "(xxx) xxx-xxxx".</span>
                        </div>
                    </div>
                </div>
                <!-- Food Preferences -->
                <div class="form-field">
                    <label for="foodPreferences">Food Preferences:</label>
                    <textarea id="foodPreferences" disabled></textarea>
                </div>
            </form>
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

        </c:otherwise>
    </c:choose>
</div>

<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>

<script>
    <c:choose>
        <c:when test="${requestScope.subscription == null}">
            const subscription = {};
        </c:when>
        <c:otherwise>
            const subscription = ${requestScope.subscription.toJson()};
        </c:otherwise>
    </c:choose>
    const cities = ${requestScope.cities};
    let filteredCities = cities;
    let foodPreferences = ${requestScope.foodPreferences};

    const form = $('#subscriptionForm');
    const provinceSelect = $('#province');
    const citySelect = $('#city');
    const methodSelect = $('#method');
    const emailInput = $('#email');
    const phoneInput = $('#phone');
    const foodPreferencesSelect = $('#foodPreferences');

    function iniCreateForm() {
        filterCityList(subscription.province);
    }

    function iniUpdateForm() {
        provinceSelect.val(subscription.province);
        filterCityList(subscription.province);
        citySelect.val(subscription.city);
        methodSelect.val(subscription.method);
        handleMethodChange();
        emailInput.val(subscription.email); // or user.email
        phoneInput.val(subscription.phone);
        updateFoodPreferences();
    }

    function iniInfoCard() {
        $("#info-province").text(subscription.province);
        $("#info-city").text(subscription.city);
        $("#info-method").text(subscription.method.toUpperCase());
        subscription.method.toLowerCase() === "email" && $("#info-email").text(subscription.email).parent("p").show();
        subscription.method.toLowerCase() === "sms" && $("#info-phone").text(subscription.phone).parent("p").show();
        $("#info-food-preferences").text(foodPreferences.map(itm => itm.description).join(", ") || "N/A");
        $(".info-card").show();
        $(".info-button").hover(function () {
            $(".info-card").addClass("active")
        }, function () {
            $(".info-card").removeClass("active")
        })
    }

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

    function handleProvinceChange() {
        const provinceId = provinceSelect.val();
        filterCityList(provinceId);
        validateProvinceField();
    }

    function handleCityChange() {
        const city = filteredCities.find(city => city.city === citySelect.val());
        if (city && city.provinceId !== provinceSelect.val()) {
            provinceSelect.val(city.provinceId);
            validateProvinceField();
        }
        validateCityField();
    }

    function handleMethodChange() {
        const method = methodSelect.val();
        const email = $('#email-field');
        const phone = $('#phone-field');
        if (method === 'email') {
            phone.fadeOut(300, function () {
                email.fadeIn(300, function () {
                    phone.hide();
                    phoneInput.next('.error-message').hide();
                    email.show();
                });
            });

        } else if (method === 'sms') {
            email.fadeOut(300, function () {
                phone.fadeIn(300, function () {
                    email.hide();
                    emailInput.next('.error-message').hide();
                    phone.show();
                });
            });

        } else {
            email.fadeOut(300, function () {
                emailInput.next('.error-message').hide();
            });
            phone.fadeOut(300, function () {
                phone.hide();
                phoneInput.next('.error-message').hide();
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
        if (isChecked) {
            foodPreferences.push(food);
        } else {
            foodPreferences = foodPreferences.filter(itm => itm.id !== food.id);
        }
        updateFoodPreferences();
    }


    function handleSubscribe() {
        validateForm();
        const data = {
            province: provinceSelect.val(),
            city: citySelect.val(),
            method: methodSelect.val(),
            foodPreferences: foodPreferences.map(food => food.id).join(',')
        }
        if (methodSelect.val() === 'email') {
            data.email = emailInput.val();
        }
        if (methodSelect.val() === 'sms') {
            data.phone = phoneInput.val();
        }
        $.ajax({
            url: 'subscription/subscribe',
            type: 'POST',
            data,
            success: function (res) {
                if (res?.code === 0) {
                    alert('Subscribe successfully!');
                    window.location.reload();
                } else {
                    alert('Failed to subscribe: ' + res?.messsage + '. Please try again.');
                }
            },
            error: function (xhr, status, error) {
                console.error('Error:', status, error);
                alert('Failed to subscribe. Please try again.');
            }
        });
    }

    function handleUpdate() {
        validateForm();
        const data = {
            consumerId: subscription.consumerId,
            province: provinceSelect.val(),
            city: citySelect.val(),
            method: methodSelect.val(),
            foodPreferences: foodPreferences.map(food => food.id).join(',')
        }
        if (methodSelect.val() === 'email') {
            data.email = emailInput.val();
        }
        if (methodSelect.val() === 'sms') {
            data.phone = phoneInput.val();
        }
        $.ajax({
            url: 'subscription/update',
            type: 'POST',
            data,
            success: function (res) {
                if (res?.code === 0) {
                    alert('Subscription updated successfully!');
                    window.location.reload();
                } else {
                    alert('Failed to update subscription: ' + res?.messsage + '. Please try again.');
                }
            },
            error: function (xhr, status, error) {
                console.error('Error:', status, error);
                alert('Failed to update subscription. Please try again.');
            }
        });
    }

    function handleDeactivate() {
        $.ajax({
            url: 'subscription/deactivate',
            type: 'GET',
            data: {consumerId: subscription.consumerId},
            success: function (res) {
                if (res?.code === 0) {
                    alert('Unsubscribe successfully!');
                    window.location.reload();
                } else {
                    alert('Failed to unsubscribe: ' + res?.messsage + '. Please try again.');
                }
            },
            error: function (xhr, status, error) {
                console.error('Error:', status, error);
                alert('Failed to unsubscribe. Please try again.');
            }
        });
    }

    function handleReactivate() {
        $.ajax({
            url: 'subscription/reactivate',
            type: 'GET',
            data: {consumerId: subscription.consumerId},
            success: function (res) {
                if (res?.code === 0) {
                    alert('Reactivate successfully!');
                    window.location.reload();
                } else {
                    alert('Failed to reactivate: ' + res?.messsage + '. Please try again.');
                }
            },
            error: function (xhr, status, error) {
                console.error('Error:', status, error);
                alert('Failed to reactivate. Please try again.');
            }
        });
    }

    function validateProvinceField() {
        return validateField(provinceSelect);
    }

    function validateCityField() {
        return validateField(citySelect);
    }

    function validateMethodField() {
        return validateField(methodSelect);
    }

    function validateEmailField() {
        const pattern = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
        return validateField(emailInput, pattern);
    }

    function validatePhoneField() {
        const pattern = /^(?:\(\d{3}\)\s?\d{3}-\d{4}|\d{3}-\d{3}-\d{4}|\d{10})$/;
        return validateField(phoneInput, pattern);
    }

    function validateField(field, pattern = /^.+$/) {
        const val = field.val().toString().trim();
        const errorMessage = field.next('.error-message');
        const isValid = val && pattern.test(val);
        isValid ? errorMessage.hide() : errorMessage.show();
        return isValid;
    }

    function validateForm() {
        let isValid = validateProvinceField();
        isValid = validateCityField() && isValid;
        isValid = validateMethodField() && isValid;
        if (methodSelect.val() === 'email') {
            isValid = validateEmailField() && isValid;
        }
        if (methodSelect.val() === 'sms') {
            isValid = validatePhoneField() && isValid;
        }
        return isValid;
    }

    function clearValidate() {
        $('.error-message').hide();
    }

    $(document).ready(function () {
        if (!subscription) {
            iniCreateForm();
            console.log('#1', foodPreferences);
        } else if (subscription.status === false) {
            iniInfoCard();
        } else {
            iniUpdateForm();
        }

    });

</script>
</body>
</html>
