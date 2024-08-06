<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Index</title>
    <link rel="stylesheet" type="text/css" href="resources/css/base.css">
    <script>

        <%
            String redirectPath = "/login"; // user is not log in or invalid user type
            if (session != null && session.getAttribute("type") != null) {
                String type = (String) session.getAttribute("type");
                if(type.equalsIgnoreCase("retailer")) redirectPath = "/food-inventory";
                if(type.equalsIgnoreCase("charitable")) redirectPath = "/claim-food";
                if(type.equalsIgnoreCase("consumer")) redirectPath = "/purchase-food";
            }
            out.print("window.location.href = '" + request.getContextPath() + redirectPath + "';");
        %>

    </script>
</head>
<body>
</body>
</html>