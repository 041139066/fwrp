<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.FoodInventory" %>
<%@ page import="java.time.LocalDateTime" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Inventory</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/food.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<nav>
    <div class="nav-left">
        <div class="link"><a href="<%= request.getContextPath() %>/food-inventory">Food Inventory</a></div>
        <div class="link"><a href="<%= request.getContextPath() %>/food-inventory/surplus-food">Surplus Food</a></div>
    </div>
    <div class="nav-right">
        <div class="link"><a href="logout.jsp">Log Out</a></div>
    </div>
</nav>

<h1><%= (boolean) request.getAttribute("isSurplus") ? "Surplus Food" :"Food Inventory" %></h1>
<div class="container card">
    <button class="button-primary" onclick="handleAdd()">Add New Inventory</button>
    <%
        List<FoodInventory> foodInventoryList = (List<FoodInventory>) request.getAttribute("foodInventoryList");
        if (foodInventoryList == null) {
    %>
    <p>No food inventory items found.</p>
    <%
    } else {
    %>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Food</th>
            <th>Price</th>
            <th>Expiration Date</th>
            <th>Surplus Status</th>
            <th>Donation/Sale</th>
            <th>Quantity</th>
            <th>Average Rating</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (FoodInventory item : foodInventoryList) {
        %>
        <tr>
            <td>
                <%= item.getId() %>
            </td>
            <td>
                <%= item.getName() %>
            </td>
            <td>
                <%= item.getPrice() %>
            </td>
            <td>
                <%= item.getStrExpirationDate() %>
            </td>
            <td>
                <%= item.getStatus() != null ? item.getStrStatus() : "N/A" %>
            </td>
            <td>
                <%= item.isSurplus() ? "Yes" : "No" %>
            </td>
            <td>
                <%= item.getQuantity() %>
            </td>
            <td>
                <%= item.getAverageRating() > 0 ? item.getAverageRating() : "N/A" %>
            </td>
            <td>
                <button class="button-warning" onclick="handleEdit(<%= item.getId() %>);">Edit</button>
                <button class="button-error" onclick="handleDelete(<%= item.getId() %>);">Delete</button>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        }
    %>
</div>
<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>
<%--<%@ include file="/food-rating/rating-modal.jsp" %>--%>
<script>

    function handleAdd() {
        window.location = "<%= request.getContextPath() %>/food-inventory/add";
    }

    function handleEdit(id) {
        window.location = "<%= request.getContextPath() %>/food-inventory/edit?id=" + id;
    }

    function handleDelete(id) {
        const isConfirmed = confirm("Do you want to delete this food inventory?");
        if (isConfirmed) {
            window.location = "<%= request.getContextPath() %>/food-inventory/delete?id=" + id;
        }
    }
</script>
</body>
</html>
