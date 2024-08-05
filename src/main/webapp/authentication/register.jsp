<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Food Waste Reduction Platform</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/authentication.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h1>Food Waste Reduction Platform</h1>
<div class="container">
    <div class="left-section">
        <h2>Welcome!</h2>
        <p>
            Our platform aims to reduce food waste by connecting retailers, consumers, and charitable organizations.
            Join us in making a difference by managing your food inventory effectively and supporting food
            redistribution.
        </p>
    </div>
    <div class="right-section">
        <h2>Register</h2>
        <form id="register-form">
            <div class="form-field">
                <label for="name">Username:</label>
                <input type="text" id="name" name="name" onchange="handleNameChange()" required>
                <span class="error-message"
                      style="display: none">Please enter your name.</span>
            </div>
            <div class="form-field">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" onchange="handleEmailChange()" required>
                <span class="error-message"
                      style="display: none">Please enter a valid email address: xxx@xxx.xx.</span>
            </div>
            <div class="row">
                <div class="form-field">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" onchange="handlePasswordChange()" required>
                    <span class="error-message"
                          style="display: none">Please enter a valid password: at least 6 letters and digits.</span>
                </div>
                <div class="form-field">
                    <label for="re-password">Enter password again:</label>
                    <input type="password" id="re-password" onchange="handleRePasswordChange()" required>
                    <span class="error-message"
                          style="display: none">Passwords do not match.</span>
                </div>
            </div>
            <div class="form-field">
                <label for="type">User Type:</label>
                <select id="type" name="type" onchange="handleTypeChange()" required>
                    <option value="">Select User Type</option>
                    <option value="retailer">Retailer</option>
                    <option value="charitable">Charitable Organization</option>
                    <option value="consumer">Consumer</option>
                </select>
                <span class="error-message"
                      style="display: none">Please select a user type.</span>
            </div>
            <div class="row" id="location" style="display: none">
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
            <div class="form-buttons">
                <button type="button" id="register-btn" class="button-primary" onclick="handleSubmit()">Register
                </button>
            </div>
            <div class="form-footer">
                Already have an account? <a href="<%= request.getContextPath() %>/login">Login</a>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="../resources/js/jquery-3.7.1.js"></script>

<script type="text/javascript">
    const cities = ${requestScope.cities};
    let filteredCities = cities;

    const form = $("#register-form");
    const name = $("#name");
    const email = $("#email");
    const password = $("#password");
    const rePassword = $("#re-password")
    const type = $("#type");
    const locationSelect = $("#location");
    const provinceSelect = $("#province");
    const citySelect = $("#city");

    $(document).ready(function () {
        filterCityList();
    });

    function handleNameChange() {
        validateName();
    }

    function handleEmailChange() {
        validateEmail();
    }

    function handlePasswordChange() {
        validatePassword();
    }

    function handleRePasswordChange() {
        validateRePassword();
    }

    function handleTypeChange() {
        type.val() === 'retailer' ? locationSelect.show() : locationSelect.hide();
        validateType();
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
        validateProvince();
    }

    function handleCityChange() {
        const city = filteredCities.find(city => city.city === citySelect.val());
        if (city && city.provinceId !== provinceSelect.val()) {
            provinceSelect.val(city.provinceId);
            validateProvince();
        }
        validateCityField();
    }

    function handleSubmit() {
        const isValid = validateForm();
        if (isValid) {
            const data = {
                name: name.val(),
                email: email.val(),
                password: password.val(),
                type: type.val()
            };
            if(type.val() === 'retailer'){
                data.province = provinceSelect.val();
                data.city = citySelect.val();
            }
            $.ajax({
                url: 'register',
                type: 'POST',
                data,
                success: function (res) {
                    if (res?.code === 0) {
                        alert('Register successfully!');
                        window.location = "<%= request.getContextPath() %>/login";
                    } else {
                        alert('Failed to register: ' + res?.message + '. Please try again.');
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Error:', status, error);
                    alert('Failed to register. Please try again.');
                }
            });
        }
    }

    function validateForm() {
        let isValid = validateName();
        isValid = validateEmail() && isValid;
        isValid = validatePassword() && isValid;
        isValid = validateRePassword() && isValid;
        isValid = validateType() && isValid;
        isValid = validateType() && isValid;
        if (type.val() === 'retailer') {
            isValid = validateProvince() && isValid;
            isValid = validateCity() && isValid;
        }
        return isValid;
    }

    function validateName() {
        return validateField(name);
    }

    function validateEmail() {
        const pattern = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
        return validateField(email, pattern);
    }

    function validatePassword() {
        const pattern = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{6,}$/;
        return validateField(password, pattern);
    }

    function validateRePassword() {
        const pattern = new RegExp("^" + password.val() + "$");
        return validateField(rePassword, pattern);
    }

    function validateType() {
        return validateField(type);
    }

    function validateProvince() {
        return validateField(provinceSelect);
    }

    function validateCity() {
        return validateField(citySelect);
    }

    function validateField(field, pattern = /^.+$/) {
        const val = field.val().toString().trim();
        const errorMessage = field.next('.error-message');
        const isValid = val && pattern.test(val);
        isValid ? errorMessage.hide() : errorMessage.show();
        return isValid;
    }
</script>

<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>
</body>
</html>
