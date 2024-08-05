<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Index</title>
    <link rel="stylesheet" type="text/css" href="resources/css/base.css">
    <script>

        <%
            String redirectPath;
            if (session != null && session.getAttribute("type") != null) {

                // User is logged in
                redirectPath = "/food-inventory";
            } else {
                // User is not logged in
                redirectPath = "/login";
            }
            out.print("window.location.href = '" + request.getContextPath() + redirectPath + "';");
        %>

    </script>
</head>
<body>
</body>
</html>