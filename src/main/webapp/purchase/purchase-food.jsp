<%@ page import="dataaccesslayer.PurchaseFoodListDao" %>
<%@ page import="dataaccesslayer.PurchaseFoodDao" %>
<%@ page import="model.AvailableSaleFood" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Purchase Food</title>
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

<%
    String id = request.getParameter("id");
    PurchaseFoodDao dao = new PurchaseFoodDao();
    AvailableSaleFood food = dao.getAvailableSaleFoodById(Integer.parseInt(id));
%>
<div class="container">
    <h1><%= food.getDescription() %></h1>
    <h1>need</h1>
    <input type="text" id="need" style="height: 50px;height: 50px;margin-left: 520px"><br>
    <input type="button" class="button button-add" style="margin-left: 550px" onClick="aa()" value="Purchase"></input>
</div>
<script type="text/javascript">
    function aa(){
        let value = document.getElementById("need").value;
        window.location.href="/purchaseFood?need="+value+"&foodItemId=<%= food.getId()%>&customerId=<%= 2%>"
        //alert("Success");
    }
</script>
</body>
</html>
