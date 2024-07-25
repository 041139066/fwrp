<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Inventory</title>
    <link rel="stylesheet" href="resources/css/main-page.css">
</head>
<body>
<nav>
    <div class="nav-left">
        <div class="link"><a href="food_inventory.jsp">Food Inventory</a></div>
        <div class="link"><a href="food_items.jsp">Food Items</a></div>
    </div>
    <div class="nav-right">
        <div class="link"><a href="logout.jsp" class="button button-logout">Log Out</a></div>
    </div>
</nav>
<h1>Food Inventory</h1>
<div class="container card">
    <button class="button-primary">Add New Inventory</button>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Standard Price</th>
            <th>Quantity</th>
            <th>Average Rating</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${requestScope.list}">
            <tr>
                <td>${item.id}</td>
                <td>${item.description}</td>
                <td>${item.standardPrice}</td>
                <td>${item.averageRating}</td>
                <td>${item.quantity}</td>
                <td>
                    <button class="button-warning">Edit</button>
                    <button class="button-error">Delete</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>
</body>
</html>
