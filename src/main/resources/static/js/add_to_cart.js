$(document).ready(function() {
    $("#buttonAddToCart").on("click", function(evt) {
        addToCart();
    });
});

function addToCart() {
    quantity = $("#quantity" + itemId).val();
    url = contextPath + "cart/add/" + itemId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url,

    }).done(function(response) {
        showModalDialog("Shopping Cart", response);
    }).fail(function() {
        showErrorModal("Error while adding product to shopping cart.");
    });
}