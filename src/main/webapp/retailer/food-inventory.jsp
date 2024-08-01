<%@ page import="java.util.List" %>
<%@ page import="model.FoodInventory" %>
<%@ page import="dataaccesslayer.FoodInventoryDAO" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Inventory</title>
    <link rel="stylesheet" type="text/css" href="../resources/css/base.css">

    <script>
        window.onload = function() {
            // Set minimum date and time for the expirationDate input
            var now = new Date();
            var year = now.getFullYear();
            var month = String(now.getMonth() + 1).padStart(2, '0');
            var day = String(now.getDate()).padStart(2, '0');
            var hours = String(now.getHours()).padStart(2, '0');
            var minutes = String(now.getMinutes()).padStart(2, '0');
            var seconds = String(now.getSeconds()).padStart(2, '0');
            var datetime = `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;

            document.getElementById('expirationDate').min = datetime;
        }
    </script>


</head>
<body>
<nav>
    <div class="nav-left">
        <div><a href="food-inventory.jsp">Food Inventory</a></div>
        <div><a href="surplus-food-inventory.jsp">Surplus Food</a></div>

        <!--<div><a href="food_items.jsp">Food Items</a></div>-->
    </div>
    <div class="nav-right">
        <div><a href="logout.jsp" class="button button-logout">Log Out</a></div>
    </div>
</nav>

<h1>Food Inventory</h1>
<div class="container">
    <a href="add_food_inventory.jsp" class="button button-add">Add New Inventory</a>
    <%
        try {
            FoodInventoryDAO dao = new FoodInventoryDAO();
            List<FoodInventory> foodInventoryList = dao.getAllFoodInventory();
            if (foodInventoryList.isEmpty()) {
    %>
    <p>No food inventory items found.</p>
    <%
    } else {
    %>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Standard Price</th>
            <th>Quantity</th>
            <th>Expiration Date</th>
            <th>Average Rating</th>

            <th>Surplus</th>
            <th>For Donation</th>
            <th>For Sale</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (FoodInventory item : foodInventoryList) {
                LocalDateTime now = LocalDateTime.now();
                boolean isSurplus = item.getExpirationDate().isBefore(now.plusWeeks(1));

        %>
        <tr>
            <td><%= item.getId() %></td>
            <td><%= item.getDescription() %></td>
            <td><%= item.getStandardPrice() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getExpirationDate() != null ? item.getExpirationDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "" %></td>
            <td><%= item.getAverageRating() %></td>

            <td><%= isSurplus ? "Yes" : "No" %></td>
            <td><%= item.getIsForDonation() ? "Yes" : "No" %></td>
            <td><%= item.getIsForSale() ? "Yes" : "No" %></td>

            <td>
                <a href="edit_food_inventory.jsp?id=<%= item.getId() %>" class="button button-edit">Edit</a>
                <a href="delete_food_inventory.jsp?id=<%= item.getId() %>" class="button button-delete" onclick="return confirm('Are you sure you want to delete this item?');">Delete</a>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
            }
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    %>
</div>






<footer>
    &copy; 2024 Food Waste Reduction Platform. All rights reserved.
</footer>
</body>
</html>
