<%--
  Created by IntelliJ IDEA.
  User: yuexia Jin
  Date: 2024-07-31
  Time: 11:04 a.m.
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Food Inventory</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/inventory.css">
</head>
<body>
<h1>Edit Food Inventory</h1>
<div class="container card inventory-form">
    <form method="post" action="add">
        <div class="form-field">
            <label for="name">Food:</label>
            <input id="name" name="name" required>
        </div>
        <div class="form-field">
            <label for="price">Price:</label>
            <input type="number" id="price" name="price" min="0" step="0.01" required>
        </div>
        <div class="form-field">
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" min="0" step="1" required>
        </div>
        <div class="form-field">
            <label for="expiration-date">Expiration Date:</label>
            <input type="datetime-local" id="expiration-date" name="expirationDate" required>
        </div>
        <div class="form-buttons">
            <button type="submit" class="button-regular">Submit</button>
            <button type="button" class="button-info" onclick="window.history.back();">Cancel</button>
        </div>
    </form>
</div>
</body>
</html>
