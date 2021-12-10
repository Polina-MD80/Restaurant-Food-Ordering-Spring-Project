$(document).ready(function() {
    $("#buttonAddToCart").on("submit", function(evt) {
        addToCart();
    });
});

function addToCart() {

    url = "http://localhost:8080/cart/add/" + itemId ;
    fetch(url).then(function(response) {
        showModalDialog("Shopping Cart", response);
    })
}