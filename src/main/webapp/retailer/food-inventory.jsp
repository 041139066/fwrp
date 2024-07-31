<%@ page import="java.util.List" %>
<%@ page import="model.FoodInventory" %>
<%@ page import="dataaccesslayer.FoodInventoryDAO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Inventory</title>
    <link rel="stylesheet" type="text/css" href="../resources/css/base.css">
</head>
<body>
<nav>
    <div class="nav-left">
        <div><a href="food-inventory.jsp">Food Inventory</a></div>
        <div><a href="food_items.jsp">Food Items</a></div>
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
            <th>Last Modified</th>
            <th>Average Rating</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (FoodInventory item : foodInventoryList) {
        %>
        <tr>
            <td><%= item.getId() %></td>
            <td><%= item.getDescription() %></td>
            <td><%= item.getStandardPrice() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getLastModified() != null ? item.getLastModified().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "" %></td>
            <td><%= item.getAverageRating() %></td>
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
