<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="rating-modal" class="modal">
    <div class="modal-content">
        <button class="icon-button close-button" onclick="closeModal()"><i class="fa-solid fa-xmark"></i></button>
        <h2 id="modal-title">Rating for Food</h2>
        <form id="rating-form">
            <div class="form-field">
                <label for="rating">Rating</label>
                <input type="number" id="rating" name="rating" min="0" max="5" step="0.5" required>
            </div>
            <div class="form-field">
                <label for="comment">Comment</label>
                <textarea id="comment" name="comment" required></textarea>
            </div>
            <div class="form-buttons">
                <button id="create-button" type="button" class="button-regular" onclick="handleCreate()" style="display:none">Create</button>
                <button id="update-button" type="button" class="button-regular" onclick="handleUpdate()" style="display:none">Update</button>
                <button type="button" class="button-info" onclick="closeModal()">Cancel</button>
            </div>
        </form>
    </div>
</div>

<style>
    .modal {
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100vw;
        height: 100vh;
        overflow: auto;
        background-color: rgb(0, 0, 0);
        background-color: rgba(0, 0, 0, 0.4);
        display: none;
    }

    .modal-content {
        position: relative;
        background-color: #fefefe;
        margin: 10% auto;
        padding: 24px;
        border-radius: 12px;
        width: 60%;
        max-width: 600px;
    }

    .modal-content .close-button {
        position: absolute;
        top: 8px;
        right: 4px;
        font-weight: bold;
    }

    .modal-content h2 {
        margin-top: 0;
    }
</style>

<script>
    const modal = $("#rating-modal");
    const form = $("#rating-form");
    const title = $("#modal-title");
    const rating = $("#rating");
    const comment = $("#comment");
    const createButton = $("#create-button");
    const updateButton = $("#update-button");

    let currentItem;

    function openUpdateModal(item) {
        title.text("Update Your Rating for " + item.foodInventoryDescription);
        if (item) {
            currentItem = item;
            rating.val(item.rating);
            comment.val(item.comment);
        }
        updateButton.show();
        modal.show();
    }

    function openCreateModal(item) {
        title.text("Create Your Rating for " + item.description);
        if (item) {
            currentItem = item;
        }
        createButton.show();
        modal.show();
    }

    function closeModal() {
        form[0].reset();
        createButton.hide();
        updateButton.hide();
        modal.hide();
    }

    function handleCreate() {
        handleSubmit({
            url: 'rating/create',
            data: {
                foodInventoryId: currentItem.id,
                rating: rating.val(),
                comment: comment.val()
            }
        }, "create");
    }

    function handleUpdate() {
        handleSubmit({
            url: 'rating/update',
            data: {
                foodInventoryId: currentItem.foodInventoryId,
                rating: rating.val(),
                comment: comment.val()
            }
        }, "update");
    }

    function handleSubmit(options, type) {
        $.ajax({
            type: 'POST',
            ...options,
            success: function (res) {
                if (res?.code === 0) {
                    alert('Rating ' + type + 'd successfully!');
                    window.location.reload();
                } else {
                    alert('Failed to ' + type + ' rating: ' + res?.message + '. Please try again.');
                }
            },
            error: function (xhr, status, error) {
                console.error('Error:', status, error);
                alert('Failed to ' + type + ' rating. Please try again.');
            }
        });
    }


</script>



