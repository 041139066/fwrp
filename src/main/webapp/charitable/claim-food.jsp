<%@ page import="java.util.List" %>
<%@ page import="dataaccesslayer.ClaimedFoodDAO" %>
<%@ page import="model.DonationFoodVO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Claim Food</title>
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

<h1>CLaim Food</h1>
<div class="container">
    <a href="my-claim-food" class="button button-add">My Claimed Food</a>

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
            ClaimedFoodDAO dao = new ClaimedFoodDAO();
            List<DonationFoodVO> inventoryList = dao.getAllDonationFood();

            for (DonationFoodVO item : inventoryList) {
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
                <input type="button" class="button button-edit" onClick="aa(<%= item.getId() %>,document.getElementById('need').value,2)" value="Claim"><%--
                <a href="/claimFood?id=<%= item.getId() %>&quantity=<%= item.getQuantity() %>&status=" class="button button-edit"
                   onclick="return confirm('Are you sure do this change');">Claim</a>--%>
                <script type="text/javascript">
                    function aa(id,q,userId){
                        window.location.href="/claimFood?id="+id+"&need="+q+"&userId="+userId;
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
<footer>
    &copy; 2024 Food Waste Reduction Platform. All rights reserved.
</footer>
</body>
</html>
