$(document).ready(function() {
    $("#buttonAddToCart").on("submit", function(evt) {
        evt.preventDefault();
        addToCart();
    });
});

function addToCart() {

    url = "http://localhost:8080/cart/add/" + itemId +"/" ;
    fetch(url).then(function(response) {
        showModalDialog("Shopping Cart", response);
    })
}