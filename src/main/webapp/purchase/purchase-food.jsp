<%@ page import="java.util.List" %>
<%@ page import="model.FoodInventory" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Purchase Food</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/consumer-charitable.css">
    <script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
</head>
<body>

<!-- Nav Bar -->
<%@ include file="/utility/nav.jsp" %>

<h1>Purchase Food</h1>
<div class="container">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Food</th>
            <th>Price</th>
            <th>Expiration Date</th>
            <th>Quantity</th>
            <th>Average Rating</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <%
            List<FoodInventory> list = (List<FoodInventory>) request.getAttribute("list");
            if (list != null && !list.isEmpty()) {
                for (FoodInventory item : list) {
        %>
        <tr>
            <td><%= item.getId() %>
            </td>
            <td><%= item.getName() %>
            </td>
            <td><%= item.getFormattedPrice() %>
            </td>
            <td><%= item.getStrExpirationDate() %>
            </td>
            <td><%= item.getQuantity() %>
            </td>
            <td>
                <button class="link-button"
                        onclick="window.location = 'rating?foodInventoryId=' + <%= item.getId() %>"
                        title="see all ratings"
                >
                    <%= item.getAverageRating() %>
                </button>
                <button class="icon-button" onclick='handleRating(<%= item.toJson()%>)' title="add your rating">
                    <i class="fa-regular fa-pen-to-square"></i>
                </button>
            </td>
            <td>
                <form class="table-form" method="post" action="<%= request.getContextPath() %>/purchase-food">
                    <input class="table-input" type="number" name="need" value="0" min="0"
                           max="<%= item.getQuantity() %>" step="1"
                    >
                    <input type="hidden" name="id" value="<%= item.getId() %>">
                    <input type="hidden" name="action" value="purchaseFood">
                    <button type="submit" class="icon-button" title="purchase this food"><i
                            class="fa-solid fa-cart-shopping"></i></button>
                </form>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="7">No food for available at the moment. Come back later.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>
<!-- Rating -->
<%@ include file="/food-rating/rating-modal.jsp" %>
<script>
    function handleRating(foodInventory) {
        const consumerRatingList = ${requestScope.consumerRatingList};
        const rating = consumerRatingList.find(itm => itm.foodInventoryId === foodInventory.id);
        rating ? openUpdateModal(rating) : openCreateModal(foodInventory);
    }
</script>
</body>
</html>
