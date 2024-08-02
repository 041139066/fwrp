<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Inventory</title>
    <link rel="stylesheet" href="resources/css/main-page.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<nav>
    <div class="nav-left">
        <div class="link"><a href="food_inventory.jsp">Food Inventory</a></div>
        <div class="link"><a href="food_items.jsp">Food Items</a></div>
    </div>
    <div class="nav-right">
        <div class="link"><a href="logout.jsp">Log Out</a></div>
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
                <td>${item.quantity}</td>
                <td>
                    <button class="link-button" onclick="window.location='rating?foodInventoryId=${item.id}'">${item.averageRating}</button>
                    <button class="icon-button" onclick='handleRating(${item.toJson()})'><i class="fa-regular fa-pen-to-square"></i></button>
                </td>
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
<%@ include file="/food-rating/rating-modal.jsp" %>
<script>
    function handleRating(foodInventory){
        const consumerRatingList = ${requestScope.consumerRatingList};
        const rating = consumerRatingList.find(itm=>itm.foodInventoryId === foodInventory.id);
        if(rating){
            openUpdateModal(rating);
        } else {
            openCreateModal(foodInventory)
        }
    }
</script>
</body>
</html>
