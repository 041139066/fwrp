<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<div class="header">
    <h1>Welcome, ${sessionScope.name}!</h1>
    <a href="LogoutServlet" class="logout-button">Logout</a> <!-- Logout link -->
</div>

<div class="content">
    <%
        String userType = (String) session.getAttribute("userType");
        if (userType != null) {
            switch(userType.toLowerCase()) {
                case "consumer":
    %>
    <%@ include file="/food-rating/consumer-rating-list.jsp" %>
    <%
            break;
        case "retailer":
    %>
    <%@ include file="/retailer/food-inventory.jsp" %>
    <%
            break;
        case "organization":
    %>
    <%@ include file="/surplus-food-alert/subscription.jsp" %>
    <%
            break;
        default:
    %>
    <p>Unknown user type. Please contact support.</p>
    <%
        }
    } else {
    %>
    <p>Error: User type not found. Please log in again.</p>
    <%
        }
    %>
</div>
</body>
</html>
