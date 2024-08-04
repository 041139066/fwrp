<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Food Waste Reduction Platform</title>
    <link rel="stylesheet" type="text/css" href="../resources/css/authentication.css">
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
        <form action="Register-servlet" method="post" id="registerForm">
            <div class="form-field">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-field">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-field">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-field">
                <label for="type">User Type:</label>
                <select id="type" name="type" required>
                    <option value="retailer">Retailer</option>
                    <option value="charitable">Charitable Organization</option>
                    <option value="consumer">Consumer</option>
                </select>
            </div>
            <div id="msg" style="color:red;">
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    if (errorMessage != null) {
                        out.println(errorMessage);
                    }
                %>
            </div>
            <div class="form-buttons">
                <button type="submit" id="registerBtn" class="button-primary">Register</button>
            </div>
            <div class="form-footer">
                Already have an account? <a href="index.jsp">Login</a>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="../JS/jquery-3.7.1.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("#registerBtn").click(function(event) {
            var name = $("#name").val();
            var email = $("#email").val();
            var password = $("#password").val();

            if(isEmpty(name)) {
                $("#msg").html("Name cannot be empty");
                event.preventDefault(); // Prevent form submission
                return;
            }

            if(isEmpty(email)) {
                $("#msg").html("Email cannot be empty");
                event.preventDefault(); // Prevent form submission
                return;
            }

            if(isEmpty(password)) {
                $("#msg").html("Password cannot be empty");
                event.preventDefault(); // Prevent form submission
                return;
            }

            $("#registerForm").submit();
        });

        function isEmpty(str) {
            return str == null || str.trim() === "";
        }
    });
</script>

<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>
</body>
</html>
