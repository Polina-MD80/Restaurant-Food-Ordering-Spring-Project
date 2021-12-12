// $(document).ready(function() {
//     $("#buttonAddToCart").on("click", function(evt) {
//         evt.preventDefault();
//         addToCart();
//     });
// });


$('#form').after('submit', function () {
    addToCart()
});

function addToCart() {

    url = "http://localhost:8080/cart/add/" + itemId + "/";
    fetch(url).then(function (response) {
        showModalDialog("Shopping Cart", response);
    })
}