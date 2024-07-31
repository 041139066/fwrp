<%--
  Created by IntelliJ IDEA.
  User: yuexia Jin
  Date: 2024-07-31
  Time: 11:04 a.m.
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="model.FoodInventory" %>
<%@ page import="dataaccesslayer.FoodInventoryDAO" %>
<%@ page import="java.time.LocalDateTime" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Food Inventory</title>
    <link rel="stylesheet" type="text/css" href="../resources/css/base.css">
</head>
<style>
    body{
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
        font-family: Arial, sans-serif;
    }
    .form-container {
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    .form-container form {
        display: flex;
        flex-direction: column;
    }
    .form-container label {
        margin-bottom: 5px;
    }
    .form-container input {
        margin-bottom: 15px;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    .form-container button {
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
</style>
<body>
<%
    // Check if the form has been submitted
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        // Retrieve form data
        String description = request.getParameter("description");
        double standardPrice = Double.parseDouble(request.getParameter("standard_price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double averageRating = Double.parseDouble(request.getParameter("average_rating"));
        String lastModifiedStr = request.getParameter("last_modified");
        LocalDateTime lastModified = LocalDateTime.parse(lastModifiedStr);

        // Create a new FoodInventory item
        FoodInventory item = new FoodInventory(description,standardPrice,quantity,averageRating,lastModified);
        item.setDescription(description);
        item.setStandardPrice(standardPrice);
        item.setQuantity(quantity);
        item.setAverageRating(averageRating);
        item.setLastModified(lastModified);

        // Add the item to the database
        FoodInventoryDAO dao = new FoodInventoryDAO();
        dao.addFoodInventory(item);

        // Redirect to the main inventory page after successful addition
        response.sendRedirect("food-inventory.jsp");
    } else {
%>
<h1>Add New Food Inventory</h1>
<form action="add_food_inventory.jsp" method="post">
    <label for="description">Description:</label>
    <input type="text" id="description" name="description" required><br><br>
    <label for="standard_price">Standard Price:</label>
    <input type="text" id="standard_price" name="standard_price" required><br><br>
    <label for="quantity">Quantity:</label>
    <input type="number" id="quantity" name="quantity" required><br><br>
    <label for="average_rating">Average Rating:</label>
    <input type="text" id="average_rating" name="average_rating" required><br><br>
    <label for="last_modified">Last Modified:</label>
    <input type="datetime-local" id="last_modified" name="last_modified" required><br><br>
    <button type="submit">Add Inventory</button>
</form>
<%
    }
%>
</body>
</html>
