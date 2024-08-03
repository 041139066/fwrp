<%--
  Created by IntelliJ IDEA.
  User: yuexi
  Date: 2024-07-31
  Time: 11:16 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dataaccesslayer.FoodInventoryDAO" %>

<html>
<head>
    <title>Delete Food Inventory</title>
</head>
<body>
<%
    int id = Integer.parseInt(request.getParameter("id"));

    FoodInventoryDAO dao = new FoodInventoryDAO();
    dao.deleteFoodInventory(id);

    response.sendRedirect("food-inventory.jsp");
%>

</body>
</html>
