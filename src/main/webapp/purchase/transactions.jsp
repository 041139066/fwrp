<%@ page import="java.util.List" %>
<%@ page import="model.PurchasedFood" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transactions</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/consumer-charitable.css">
</head>
<body>

<%@ include file="/utility/nav.jsp" %>

<h1>Transactions</h1>
<div class="container">
    <table>
        <thead>
        <tr>
            <th>Food ID</th>
            <th>Food</th>
            <th>Purchase Quantity</th>
            <th>Cost</th>
            <th>Purchase Date</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<PurchasedFood> transactions = (List<PurchasedFood>) request.getAttribute("list");
            if (transactions != null && !transactions.isEmpty()) {
                for (PurchasedFood item : transactions) {
        %>
        <tr>
            <td><%= item.getFoodInventoryId() %></td>
            <td><%= item.getFoodInventoryName() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getFormattedCost() %></td>
            <td><%= item.getStrPurchaseDate() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5">No transactions found.</td>
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
