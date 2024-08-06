<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error Page</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/error.css">
</head>
<body>
<div class="container">
    <div class="info-message">
        <p><i class="fa-solid fa-face-sad-cry"></i> Something went wrong!</p>
        <p>
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
                    out.print(errorMessage);
                } else {
                    out.print("An unknown error occurred.");
                }
            %>
        </p>
    </div>
    <button class="button-primary" onclick="window.history.back();">Go Back!</button>
</div>
</body>
</html>
