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
<h1>My Ratings</h1>
<div class="container card">
    <c:forEach var="rating" items="${requestScope.ratingList}">
        <div class="rating-card">
            <div class="card-header">
                <div class="title">NO.${rating.foodInventoryId} - ${rating.foodInventoryDescription}</div>
                <div>
                    <button class="icon-button" onclick='openUpdateModal(${rating.toJson()})'><i
                            class="fa-regular fa-pen-to-square"></i></button>
                    <button class="icon-button-error" onclick='handleDelete(${rating.toJson()})'><i
                            class="fa-regular fa-trash-can"></i></button>
                </div>
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
<%@ include file="rating-modal.jsp" %>

<script>
    function handleDelete(rating) {
        const isConfirmed = confirm("Are you sure to delete this rating?");
        if (isConfirmed) {
            $.ajax({
                url: 'rating/delete',
                type: 'POST',
                data: {
                    consumerId: rating.consumerId,
                    foodInventoryId: rating.foodInventoryId
                },
                success: function (res) {
                    if (res?.code === 0) {
                        alert('Delete successfully!');
                        window.location.reload();
                    } else {
                        alert('Failed to delete: ' + res?.message + '. Please try again.');
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Error:', status, error);
                    alert('Failed to delete. Please try again.');
                }
            });
        }

    }
</script>
</body>
</html>
