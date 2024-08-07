<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Food Waste Reduction Platform</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/authentication.css">
    <script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
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
        <h2 class="form-header">Login</h2>
        <form id="login-form">
            <div class="form-field">
                <label for="email">Email:</label>
                <input id="email" type="email">
                <span class="error-message"
                      style="display: none">Please enter a valid email address: xxx@xxx.xx.</span>
            </div>
            <div class="form-field">
                <label for="password">Password:</label>
                <input id="password" type="password">
                <span class="error-message"
                      style="display: none">Please enter a password.</span>
            </div>
            <div class="form-buttons">
                <button type="button" id="login-button" class="button-primary" onclick="handleSubmit()">Login
                </button>
            </div>
            <div class="form-footer">
                Don't have an account? <a href="<%= request.getContextPath() %>/register">Register</a>
            </div>
        </form>
    </div>
</div>
<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>

<script>
    const form = $("#login-form");
    const email = $("#email");
    const password = $("#password");

    function handleSubmit() {
        let isValid = validateEmail();
        isValid = validatePassword() && isValid;
        if (isValid) {
            $.ajax({
                url: 'login',
                type: 'POST',
                data: {
                    email: email.val(),
                    password: password.val(),
                },
                success: function (res) {
                    if (res?.code === 0) {
                        alert('Login successfully!');
                        window.location = "<%= request.getContextPath() %>";
                    } else {
                        alert('Failed to login: ' + res?.message + '. Please try again.');
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Error:', status, error);
                    alert('Failed to login. Please try again.');
                }
            });
        }
    }

    function validateEmail() {
        const pattern = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
        return validateField(email, pattern);
    }

    function validatePassword() {
        return validateField(password);
    }

    function validateField(field, pattern = /^.+$/) {
        const errorMessage = field.next('.error-message');
        const val = field.val().toString().trim();
        const isValid = val && pattern.test(val);
        isValid ? errorMessage.hide() : errorMessage.show();
        return isValid;
    }
</script>
</body>
</html>
