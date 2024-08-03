<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Food Waste Reduction Platform</title>
    <link rel="stylesheet" type="text/css" href="resources/css/authentication.css">
</head>
<body>

<div class="main">
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
            <form action="LoginServlet" method="post" id="loginForm">
                <div class="form-field">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">
                </div>
                <div class="form-field">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div id="msg" style="color:red;">
                    <%
                        String errorMessage = (String)request.getAttribute("errorMessage");
                        if (errorMessage != null) {
                            out.print(errorMessage);
                        }
                    %>
                </div>
                <div class="form-buttons">
                    <button type="submit" id="loginBtn">Login</button>
                </div>
                <div class="form-footer">
                    Don't have an account? <a href="register.jsp">Register</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript" src="JS/jquery-3.7.1.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $("#loginBtn").click(function(event) {
            var uname = $("#email").val();
            var upwd = $("#password").val();

            if(isEmpty(uname)) {
                $("#msg").html("Email cannot be empty");
                event.preventDefault(); // Prevent form submission
                return;
            }

            if(isEmpty(upwd)) {
                $("#msg").html("Password cannot be empty");
                event.preventDefault(); // Prevent form submission
                return;
            }

            $("#loginForm").submit();
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
