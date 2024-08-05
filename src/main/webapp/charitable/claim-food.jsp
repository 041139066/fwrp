<%@ page import="java.util.List" %>
<%@ page import="model.FoodInventory" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Donation Food</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/donation.css">
</head>
<body>

<%@ include file="/utility/nav.jsp" %>

<h1>Donation Food</h1>
<div class="container">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Food</th>
            <th>Price</th>
            <th>Expiration Date</th>
            <th>Quantity</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<FoodInventory> list = (List<FoodInventory>) request.getAttribute("list");
            for (FoodInventory item : list) {
        %>
        <tr>
            <td><%= item.getId() %></td>
            <td><%= item.getName() %></td>
            <td><%= item.getFormattedPrice() %></td>
            <td><%= item.getStrExpirationDate() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getStatus() %></td>
            <td>
                <form class="table-form" method="post" action="<%= request.getContextPath() %>/claim-food">
                    <input type="number" name="need" value="0" min="0" max="<%= item.getQuantity() %>" step="1" class="table-checkbox">
                    <input type="hidden" name="id" value="<%= item.getId() %>">
                    <input type="hidden" name="action" value="claimFood">
                    <button type="submit" class="icon-button" title="claim this food"><i class="fa-solid fa-cart-shopping"></i></button>
                </form>
            </td>
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
