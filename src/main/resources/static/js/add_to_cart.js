$(document).ready(function () {
    $("#buttonAddToCart").on('click', function () {
        addToCart();
    });
});

function addToCart() {
    quantity = $("#quantity" + itemId).val();

    url = contextPath +  "cart/add/" + itemId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url
    }).done(function (response){
        alert("added to cart")
    }).fail(function (){
        alert("item not available")
    });
}
