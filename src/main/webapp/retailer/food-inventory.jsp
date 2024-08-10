<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.List" %>
<%@ page import="model.FoodInventory" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Inventory</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/inventory.css">
    <script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
</head>
<body>

<!-- Navigation Bar -->
<%@ include file="/utility/nav.jsp" %>

<h1>Food Inventory</h1>
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
                <%= item.getFormattedPrice() %>
            </td>
            <td>
                <%= item.getStrExpirationDate() %>
            </td>
            <td>
                <%= item.isSurplus() ? "Yes" : "No" %>
            </td>
            <td>
                <%= item.getStatus() != null ? item.getStrStatus() : "N/A" %>
            </td>
            <td>
                <%= item.getQuantity() %>
            </td>
            <td>
                <%= item.getAverageRating() %>
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
