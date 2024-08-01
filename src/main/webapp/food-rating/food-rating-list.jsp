<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Inventory</title>
    <link rel="stylesheet" href="resources/css/rating.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<nav>
    <div class="nav-left">
        <div class="link"><a href="/subscription">Subscription</a></div>
    </div>
    <div class="nav-right">
        <div class="link"><a href="logout.jsp">Log Out</a></div>
    </div>
</nav>
<h1>Ratings for ${requestScope.foodInventory.description}</h1>
<div class="container card">
    <button class="button-primary"
            onclick="window.history.back();">Close
    </button>
    <c:forEach var="rating" items="${requestScope.ratingList}">
        <div class="rating-card">
            <div class="card-header">
                <div class="title">NO.${rating.consumerId} - ${rating.consumerName}</div>
            </div>
            <div class="card-body">
                <p><strong>Rating:</strong> <span>${rating.rating}</span></p>
                <p><strong>Comment:</strong> <span>${rating.comment}</span></p>
                <p><strong>Last Modified:</strong> <span>${rating.getStrLastModified()}</span></p>
            </div>
        </div>
    </c:forEach>
</div>
<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>
</body>
</html>
