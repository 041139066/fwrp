<%@ page import="java.util.List" %>
<%@ page import="model.PurchasedFood" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transactions</title>
    <link rel="stylesheet" href="../resources/css/main-page.css">
</head>
<body>
<nav>
    <div class="nav-left">
        <div><a href="../food_inventory.jsp">Food Inventory</a></div>
        <div><a href="../food_items.jsp">Food Items</a></div>
    </div>
    <div class="nav-right">
        <div><a href="../logout.jsp" class="button button-logout">Log Out</a></div>
    </div>
</nav>

<h1>Transactions</h1>
<div class="container">
    <a href="purchase-food?action=listAvailableFood" class="button button-add">Back to Purchase Food</a>

    <table align="center">
        <thead>
        <tr>
            <th>Food Item ID</th>
            <th>Consumer ID</th>
            <th>Purchase Date</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<PurchasedFood> transactions = (List<PurchasedFood>) request.getAttribute("list");

            if (transactions != null && !transactions.isEmpty()) {
                for (PurchasedFood item : transactions) {
        %>
        <tr align="center">
            <td><%= item.getFoodItemId() %></td>
            <td><%= item.getConsumerId() %></td>
            <td><%= item.getPurchaseDate() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr align="center">
            <td colspan="3">No transactions found.</td>
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
