<%--
  Created by IntelliJ IDEA.
  User: yuexia Jin
  Date: 2024-07-31
  Time: 11:08 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="model.FoodInventory" %>
<%@ page import="dataaccesslayer.FoodInventoryDAO" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Food Inventory</title>
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
    // Initialize DAO
    FoodInventoryDAO dao = new FoodInventoryDAO();
    FoodInventory item = null;
    String errorMessage = "";

    // Check if the form has been submitted
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        // Retrieve form data
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String description = request.getParameter("description");
            double standardPrice = Double.parseDouble(request.getParameter("standard_price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double averageRating = Double.parseDouble(request.getParameter("average_rating"));
            String lastModifiedStr = request.getParameter("last_modified");
            LocalDateTime lastModified = LocalDateTime.parse(lastModifiedStr);

            // Update the FoodInventory item
            item = new FoodInventory();
            item.setId(id);
            item.setDescription(description);
            item.setStandardPrice(standardPrice);
            item.setQuantity(quantity);
            item.setAverageRating(averageRating);
            item.setLastModified(lastModified);

            dao.updateFoodInventory(item);

            // Redirect to the main inventory page after successful update
            response.sendRedirect("food-inventory.jsp");
            return; // Ensure no further processing after redirect
        } catch (Exception e) {
            errorMessage = "Error processing the form: " + e.getMessage();
        }
    } else {
        // Retrieve the item to edit
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            item = dao.getFoodInventoryById(id);
            if (item == null) {
                errorMessage = "No item found with ID: " + id;
            }
        } catch (Exception e) {
            errorMessage = "Error retrieving the item: " + e.getMessage();
        }
    }

    if (item != null) {
%>
<h1>Edit Food Inventory</h1>
<form action="edit_food_inventory.jsp" method="post">
    <input type="hidden" name="id" value="<%= item.getId() %>">
    <label for="description">Description:</label>
    <input type="text" id="description" name="description" value="<%= item.getDescription() %>" required><br><br>
    <label for="standard_price">Standard Price:</label>
    <input type="text" id="standard_price" name="standard_price" value="<%= item.getStandardPrice() %>" required><br><br>
    <label for="quantity">Quantity:</label>
    <input type="number" id="quantity" name="quantity" value="<%= item.getQuantity() %>" required><br><br>
    <label for="average_rating">Average Rating:</label>
    <input type="text" id="average_rating" name="average_rating" value="<%= item.getAverageRating() %>" required><br><br>
    <label for="last_modified">Last Modified:</label>
    <input type="datetime-local" id="last_modified" name="last_modified" value="<%= item.getLastModified() != null ? item.getLastModified().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "" %>" required><br><br>
    <button type="submit">Update Inventory</button>
</form>
<%
} else {
%>
<p><%= errorMessage %></p>
<%
    }
%>
</body>
</html>
