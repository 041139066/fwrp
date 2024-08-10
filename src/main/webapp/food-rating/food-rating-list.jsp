<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Inventory</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/rating.css">
    <script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
</head>
<body>

<!-- Navigation Bar -->
<%@ include file="/utility/nav.jsp" %>

<h1>Ratings for ${requestScope.foodInventory.name}</h1>
<c:choose>
    <c:when test="${requestScope.ratingList == null || requestScope.ratingList.size() == 0}">
        <div class="container center-container">
            <p class="info-message"><i class="fa-regular fa-face-sad-cry"></i> There is no ratings for this food yet.</p>
            <button class="button-primary"
                    onclick="window.history.back();">Go back!
            </button>
        </div>
    </c:when>
    <c:otherwise>
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
    </c:otherwise>
</c:choose>
<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>
</body>
</html>
