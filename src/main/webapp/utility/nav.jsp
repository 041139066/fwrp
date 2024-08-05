<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav>
    <div class="nav-left">
        <c:if test="${sessionScope.type == 'retailer'}">
            <div class="link"><a href="<%= request.getContextPath() %>/food-inventory">Food Inventory</a></div>
            <div class="link"><a href="<%= request.getContextPath() %>/food-inventory/surplus-food">Surplus Food</a></div>
        </c:if>
        <c:if test="${sessionScope.type == 'consumer'}">
            <div class="link"><a href="<%= request.getContextPath() %>/purchase-food">Purchase Food</a></div>
            <div class="link"><a href="<%= request.getContextPath() %>/subscription">Surplus Food Alert Subscription</a></div>
            <div class="link"><a href="<%= request.getContextPath() %>/rating">My Food Ratings</a></div>
        </c:if>
        <c:if test="${sessionScope.type == 'charitable'}">
            <div class="link"><a href="<%= request.getContextPath() %>/claim-food">Claim Food</a></div>
        </c:if>
    </div>
    <div class="nav-right">
        <button class="icon-button" onclick="handleLogout()"><i class="fa-solid fa-arrow-right-from-bracket"></i></button>
    </div>
</nav>

<script>
    function handleLogout(){
        const isConfirmed = confirm("Are you sure to log out?");
        if (isConfirmed) {
            $.ajax({
                url: "<%= request.getContextPath() %>/logout",
                type: 'GET',
                success: function (res) {
                    if (res?.code === 0) {
                        alert('Log out successfully!');
                        window.location = "<%= request.getContextPath() %>/login";
                    } else {
                        alert('Failed to log out: ' + res?.message + '. Please try again.');
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Error:', status, error);
                    alert('Failed to log out. Please try again.');
                }
            });
        }
    }
</script>
