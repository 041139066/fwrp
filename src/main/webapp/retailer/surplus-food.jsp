<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.List" %>
<%@ page import="model.FoodInventory" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Inventory</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/inventory.css">
    <script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script>
</head>
<body>
<%@ include file="/utility/nav.jsp" %>

<h1>Surplus Food</h1>
<div class="container card">
    <!-- Batch actions -->
    <div class="batch-actions">
        <button class="button-primary" onclick="batchSetStatus('sale')">Set For Sale</button>
        <button class="button-primary" onclick="batchSetStatus('donation')">Set For Donation</button>
    </div>
    <%
        List<FoodInventory> foodInventoryList = (List<FoodInventory>) request.getAttribute("foodInventoryList");
        if (foodInventoryList == null) {
    %>
    <p>No food inventory items found.</p>
    <%
    } else {
    %>
    <table>
        <thead>
        <tr>
            <th>Check</th>
            <th>ID</th>
            <th>Food</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Average Rating</th>
            <th>Expiration Date</th>
            <th>Surplus Status</th>
            <th>Donation/Sale</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (FoodInventory item : foodInventoryList) {
        %>
        <tr>
            <td>
                <input value="<%= item.getId() %>" type="checkbox">
            </td>
            <td>
                <%= item.getId() %>
            </td>
            <td>
                <%= item.getName() %>
            </td>
            <td>
                <%= item.getFormattedPrice() %>
            </td>
            <td>
                <%= item.getQuantity() %>
            </td>
            <td>
                <%= item.getAverageRating() %>
            </td>
            <td>
                <%= item.getStrExpirationDate() %>
            </td>
            <td>
                <%= item.isSurplus() ? "Yes" : "No" %>
            </td>
            <td>
                <select id="status_<%= item.getId() %>" class="table-select">
                    <option value="">N/A</option>
                    <option value="sale" <%= "sale".equalsIgnoreCase(item.getStrStatus()) ? "selected" : "" %>>Sale
                    </option>
                    <option value="donation" <%= "donation".equalsIgnoreCase(item.getStrStatus()) ? "selected" : "" %>>
                        Donation
                    </option>
                </select>
                <button class="button-warning" onclick="handleUpdate(<%= item.getId() %>);">Update</button>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        }
    %>
</div>
<!-- Footer -->
<%@ include file="/utility/footer.jsp" %>
<script>

    function batchSetStatus(status) {
        const ids = $('input:checked').map(function () {
            return $(this).val();
        }).get();
        updateStatus(ids, status);
    }

    function handleUpdate(id) {
        updateStatus([id], $("#status_" + id).val());
    }

    function updateStatus(ids, status) {
        if (ids.length === 0) {
            alert("Please select a food.");
            return;
        }
        const isConfirmed = confirm("Are you sure to set the food for " + status + ".");
        if (isConfirmed) {
            $.ajax({
                url: 'updateStatus',
                type: 'POST',
                data: {ids: ids.join(","), status},
                success: function (res) {
                    if (res?.code === 0) {
                        alert('Status updated successfully!');
                        window.location.reload();
                    } else {
                        alert('Failed to update status: ' + res?.message + '. Please try again.');
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Error:', status, error);
                    alert('Failed to update status. Please try again.');
                }
            });
        }
    }

</script>
</body>
</html>
