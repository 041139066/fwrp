<%--
  Created by IntelliJ IDEA.
  User: yuexia Jin
  Date: 2024-07-31
  Time: 11:08 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="model.FoodInventory" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Food Inventory</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/inventory.css">
</head>
<body>
<%
    FoodInventory item = (FoodInventory) request.getAttribute("foodInventory");
    if (item != null) {
%>
<h1>Edit Food Inventory</h1>
<div class="container card inventory-form">
    <form method="post" action="update">
        <input type="hidden" name="id" value="<%= item.getId() %>">
        <div class="form-field">
            <label for="name">Food:</label>
            <input id="name" name="name" value="<%= item.getName() %>" required>
        </div>
        <div class="form-field">
            <label for="price">Price:</label>
            <input type="number" id="price" name="price" value="<%= item.getPrice() %>" min="0" step="0.01" required>
        </div>
        <div class="form-field">
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" min="0" step="1" value="<%= item.getQuantity() %>"
                   required>
        </div>
        <div class="form-field">
            <label for="expiration-date">Expiration Date:</label>
            <input type="datetime-local" id="expiration-date" name="expirationDate"
                   value="<%= item.getLocalExpirationDate() %>" required>
        </div>
        <div class="form-buttons">
            <button type="submit" class="button-regular">Update</button>
            <button type="button" class="button-info" onclick="window.history.back();">Cancel</button>
        </div>
    </form>
</div>
<%
} else {
%>
<div class="info-card active">
    <h3>Food Inventory NOT Found!</h3>
</div>
<%
    }
%>
</body>
</html>
