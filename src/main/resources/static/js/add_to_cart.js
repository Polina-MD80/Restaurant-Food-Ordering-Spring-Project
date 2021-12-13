$(document).ready(function() {
    $("#buttonAddToCart").on("click", function(evt) {
        evt.preventDefault();
        addToCart();
    });
});

function addToCart() {
    quantity = $("#quantity" + itemId).val();
    url = contextPath + "cart/add/" + itemId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function(response) {
        showModalDialog("Shopping Cart", response);
    }).fail(function() {
        showErrorModal("Error while adding product to shopping cart.");
    });
}