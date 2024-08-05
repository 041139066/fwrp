<%@ page import="java.util.List" %>
<%@ page import="model.FoodInventory" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Purchase Food</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/donation.css">
    <script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
</head>
<body>
<%@ include file="/utility/nav.jsp" %>

<h1>Purchase Food</h1>
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
        <tr align="center">
            <td><%= item.getId() %></td>
            <td><%= item.getName() %></td>
            <td><%= item.getFormattedPrice() %></td>
            <td><%= item.getStrExpirationDate() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getStatus() %></td>
            <td>
                <form class="table-form" method="post" action="<%= request.getContextPath() %>/purchase-food">
                    <input type="number" name="need" value="0" min="0" max="<%= item.getQuantity() %>" step="1" class="table-checkbox">
                    <input type="hidden" name="id" value="<%= item.getId() %>">
                    <input type="hidden" name="action" value="purchaseFood">
                    <button type="submit" class="icon-button" title="purchase this food"><i class="fa-solid fa-cart-shopping"></i></button>
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
