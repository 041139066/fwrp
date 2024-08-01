<%@ page import="java.util.List" %>
<%@ page import="model.DonationFoodVO" %>
<%@ page import="dataaccesslayer.PurchaseFoodListDao" %>
<%@ page import="model.AvailableSaleFood" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sale Food List</title>
    <link rel="stylesheet" href="resources/css/main-page.css">
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/axios/0.21.1/axios.min.js"></script>
</head>
<body>
<nav>
    <div class="nav-left">
        <div><a href="food_inventory.jsp">Food Inventory</a></div>
        <div><a href="food_items.jsp">Food Items</a></div>
    </div>
    <div class="nav-right">
        <div><a href="logout.jsp" class="button button-logout">Log Out</a></div>
    </div>
</nav>

<h1>Sale Food List</h1>
<div class="container">

    <table align="center">
        <thead>
        <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Need</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <%
            // Example: Fetch the list of food inventory items from DAO
            PurchaseFoodListDao dao= new PurchaseFoodListDao();
            List<AvailableSaleFood> saleFoodList = dao.getAllSaleFood();

            for (AvailableSaleFood item : saleFoodList) {
        %>
        <tr align="center">
            <td><%= item.getId() %>
            </td>
            <td><%= item.getDescription() %>
            </td>
            <td><%= item.getPrice()%>
            </td>
            <td><%= item.getQuantity() %>
            </td>
            <td>
                <input type="number" id="need" value="0" style="height: 30px;width:50px">
            </td>
            <td><%= item.getStatus()%>
            </td>
            <td>
<%--
                <a href="purchase-food?id=<%= item.getId() %>" class="button button-edit" id="purchase">Purchase</a>
--%>
                <input type="button" class="button button-edit" onClick="aa(<%= item.getId() %>,document.getElementById('need').value,2)" value="Purchase"><%--
                <a href="/claimFood?id=<%= item.getId() %>&quantity=<%= item.getQuantity() %>&status=" class="button button-edit"
                   onclick="return confirm('Are you sure do this change');">Claim</a>--%>
                <script type="text/javascript">
                    function aa(id,q,userId){
                        window.location.href="/purchaseFood?need="+q+"&foodItemId="+id+"&customerId="+userId;
                    }
                </script>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

<script type="text/javascript">

</script>
<footer>
    &copy; 2024 Food Waste Reduction Platform. All rights reserved.
</footer>
</body>
</html>
