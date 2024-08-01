<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="model.FoodInventory" %>
<%@ page import="dataaccesslayer.FoodInventoryDAO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Surplus Food Inventory</title>
    <link rel="stylesheet" type="text/css" href="../resources/css/base.css">
</head>
<body>
<nav>
    <div class="nav-left">
        <div><a href="food-inventory.jsp">Food Inventory</a></div>
        <div><a href="surplus-food-inventory.jsp">Surplus Food</a></div>
    </div>
    <div class="nav-right">
        <div><a href="logout.jsp" class="button button-logout">Log Out</a></div>
    </div>
</nav>

<h1>Surplus Food Items</h1>
<div class="container">
    <%
       //List<FoodInventory> foodInventoryList = (List<FoodInventory>) request.getAttribute("foodInventoryList");

        FoodInventoryDAO dao = new FoodInventoryDAO();
        //List<FoodInventory> foodInventoryList = dao.getAllFoodInventory();
       // List<FoodInventory> surplusFoodList = null;
        //dao.getSurplusFoodInventory();

        try {
            dao.updateSurplusStatus();
            // Fetch surplus items directly
            List<FoodInventory> surplusFoodList = dao.getSurplusFoodInventory();
            //surplusFoodList = dao.getSurplusFoodInventory();

       // } catch (Exception e) {
         //   e.printStackTrace();
           // out.println("<p>Error retrieving surplus items: " + e.getMessage() + "</p>"); // Show user-friendly error

            // Log error
        //}



        //List<FoodInventory> surplusFoodItems = new java.util.ArrayList<>();
       // if (surplusFoodList != null) {
         //   for (FoodInventory item : surplusFoodList) {
                //if ("Yes".equalsIgnoreCase(item.getIsSurplus())) {
                  //  surplusFoodItems.add(item);
                //}

            //    if (item.getIsSurplus() != null && item.getIsSurplus()) {
              //      surplusFoodList.add(item);
                //}
            //}
        //}

        //if (surplusFoodList.isEmpty())
        if (surplusFoodList == null || surplusFoodList.isEmpty())
        { %>
    <p>No surplus food items found.</p>
    <%
    } else {
    %>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Standard Price</th>
            <th>Quantity</th>
            <th>Expiration Date</th>
            <th>Average Rating</th>
            <th>Surplus</th>
            <th>For Donation</th>
            <th>For Sale</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (FoodInventory item : surplusFoodList) {
        %>
        <tr>
            <td><%= item.getId() %></td>
            <td><%= item.getDescription() %></td>
            <td><%= item.getStandardPrice() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getExpirationDate() != null ? item.getExpirationDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "" %></td>
            <td><%= item.getAverageRating() %></td>
            <td>Yes</td>
            <td><%= item.getIsForDonation() ? "Yes" : "No" %></td>
            <td><%= item.getIsForSale() ? "Yes" : "No" %></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        }
        } catch (Exception e) {
        e.printStackTrace();
        out.println("<p>Error retrieving surplus items: " + e.getMessage() + "</p>"); // Show user-friendly error

        // Log error
        }
    %>
</div>

<footer>
    &copy; 2024 Food Waste Reduction Platform. All rights reserved.
</footer>
</body>
</html>

