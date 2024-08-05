<%@ page import="java.util.List" %>
<%@ page import="model.ClaimedFood" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Claimed Food</title>
    <link rel="stylesheet" href="resources/css/main-page.css">
</head>
<body>
<nav>
    <div class="nav-left">
        <div><a href="food_inventory.jsp">Food Inventory</a></div>
        <div><a href="food_items.jsp">Food Items</a></div>
    </div>
    <div class="nav-right">
        <div><a href="logout.jsp" class="button button-logout">Log Out</a></div>
    </div>
</nav>

<h1>My Claimed Food</h1>
<div class="container">
    <a href="ClaimFoodServlet?action=listDonationFood" class="button button-add">Back to Claim Food</a>

    <table align="center">
        <thead>
        <tr>
            <th>Charitable ID</th>
            <th>Food Item ID</th>
            <th>Claim Date</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<ClaimedFood> claimedFoodList = (List<ClaimedFood>) request.getAttribute("list");
            if (claimedFoodList != null && !claimedFoodList.isEmpty()) {
                for (ClaimedFood item : claimedFoodList) {
        %>
        <tr align="center">
            <td><%= item.getCharitableId() %></td>
            <td><%= item.getFoodItemId() %></td>
            <td><%= item.getClaimDate() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr align="center">
            <td colspan="3">No claimed food found.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
<footer>
    &copy; 2024 Food Waste Reduction Platform. All rights reserved.
</footer>
</body>
</html>
