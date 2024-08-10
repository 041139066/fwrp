<%@ page import="java.util.List" %>
<%@ page import="model.ClaimedFood" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Claimed Food</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/consumer-charitable.css">
</head>
<body>

<!-- Navigation Bar -->
<%@ include file="/utility/nav.jsp" %>

<h1>My Claimed Food</h1>
<div class="container">
    <table>
        <thead>
        <tr>
            <th>Food ID</th>
            <th>Food</th>
            <th>Claim Quantity</th>
            <th>Claim Date</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<ClaimedFood> claimedFoodList = (List<ClaimedFood>) request.getAttribute("list");
            if (claimedFoodList != null && !claimedFoodList.isEmpty()) {
                for (ClaimedFood item : claimedFoodList) {
        %>
        <tr>
            <td><%= item.getFoodInventoryId() %></td>
            <td><%= item.getFoodInventoryName() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getStrClaimDate() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="4">No claimed food found.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>
</body>
</html>
