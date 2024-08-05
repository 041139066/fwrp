<%@ page import="java.util.List" %>
<%@ page import="dataaccesslayer.PurchaseFoodDAO" %>
<%@ page import="model.AvailableFood" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Purchase Food</title>
    <link rel="stylesheet" href="../resources/css/main-page.css">
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/axios/0.21.1/axios.min.js"></script>
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

<h1>Purchase Food</h1>
<div class="container">
    <a href="purchase-food?action=transactions" class="button button-add">My Transactions</a>

    <table align="center">
        <thead>
        <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Need</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <%
            PurchaseFoodDAO dao = new PurchaseFoodDAO();
            List<AvailableFood> inventoryList = dao.getAllAvailableFood();

            for (AvailableFood item : inventoryList) {
        %>
        <tr align="center">
            <td><%= item.getId() %></td>
            <td><%= item.getDescription() %></td>
            <td><%= item.getPrice() %></td>
            <td><%= item.getQuantity() %></td>
            <td>
                <form method="post" action="purchase-food">
                    <input type="number" name="need" value="0" style="height: 30px;width:50px">
                    <input type="hidden" name="id" value="<%= item.getId() %>">
                    <input type="hidden" name="action" value="purchaseFood">
            </td>
            <td><%= item.getStatus() %></td>
            <td>
                    <input type="submit" class="button button-edit" value="Purchase">
                </form>
            </td>
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
